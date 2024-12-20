package com.stone.net.calladapter.async

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.intercepted
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AsyncResponseFlowCallAdapter<R>(private val responseBodyType: R) : CallAdapter<R, Flow<Response<R>>> {

    override fun responseType() = responseBodyType as Type

    override fun adapt(call: Call<R>): Flow<Response<R>> = asyncResponseFlow(call)
}

fun <R> asyncResponseFlow(call: Call<R>): Flow<Response<R>> = flow {
    try {
        suspendCancellableCoroutine<Response<R>> { continuation ->
            continuation.invokeOnCancellation {
                call.cancel()
            }
            call.enqueue(object : Callback<R> {
                override fun onResponse(call: Call<R>, response: Response<R>) {
                    continuation.resume(response)
                }

                override fun onFailure(call: Call<R>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }.let {
            emit(it)
        }
    } catch (e: Exception) {
        suspendCoroutineUninterceptedOrReturn<Nothing> { continuation ->
            Dispatchers.Default.dispatch(continuation.context) {
                continuation.intercepted().resumeWithException(e)
            }
            COROUTINE_SUSPENDED
        }
    }
}