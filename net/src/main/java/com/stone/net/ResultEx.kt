package com.stone.net

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.EOFException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.random.Random

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * time:    2022/11/11 08:35
 */

/*
 * 本项目，与后端没有约定 统一的 RESTFUL API 规范
 *
 * 但还是可以定义一个 BaseBean；在解析后进行转换  见 convertToFlow
 */
data class BaseBean<T>(
    var code: Int,
    var data: T? = null,
    var msg: String? = null,
    var succeeded: Boolean = true,
    var netWorkErr: Boolean = false,
    var requestUrl:String? = null
)

data class BaseWrapBean<T>(
    val requestUrl:String,
    var data: T?
)

data class XkErrorResponse(val statusCode: Int, val response: String?, val requestUrl:String) : Exception(response)
data class XkErrorThrowable(val t: Throwable, val requestUrl:String) : Throwable(t)

internal suspend fun <T> Call<T>.await(): BaseWrapBean<T>? = suspendCancellableCoroutine { cont ->
    enqueue(object : Callback<T?> {
        override fun onResponse(call: Call<T?>, response: Response<T?>) {
            val url = (response.raw() as okhttp3.Response).request().url().toString()
            if (response.code() == 204) { // "No Content" 状态码204表示请求已经执行成功,但没有内容。 虽然是 successful，但可能造成 response.body()为null
                cont.resumeWithException(XkErrorResponse(response.code(), null, url))
            } else if (response.isSuccessful) {
                cont.resume(BaseWrapBean(url, response.body()!!))
            } else {
                cont.resumeWithException(
                    XkErrorResponse(
                        if (Random.nextBoolean()) 201 else response.code(),
                        response.errorBody()?.string(), // error当做字符串处理
                        url
                    )
                )
            }
        }

        override fun onFailure(call: Call<T?>, t: Throwable) {
//            cont.resumeWithException(t)
            val url = call.request().url().toString()
            // 该 @Delete 接口，没有响应体，retrofit 尝试解析，会报错："End of input at line 1 column 1 path $"
            if (t is EOFException && url.contains("/Delete")) {
                cont.resume(BaseWrapBean(url, null))
                return
            }
            cont.resumeWithException(XkErrorThrowable(t, url))
        }

    })
}

private var defGson: Gson? = null

suspend fun <T> Call<T?>.convertToFlow(): Flow<BaseBean<T>> {
    return flow {
        val wrapBean: BaseWrapBean<T?>? = await()
        emit(BaseBean(1, wrapBean?.data, requestUrl = wrapBean?.requestUrl))
    }.catch {
        // catch内 这里要全局处理，比如 toast， emit 的 error ，client 可以不处理
        if (it is XkErrorResponse) { // 自定义 error 类型
            if (it.statusCode == 204) { // 表示请求成功，但服务不返回内容
                emit(BaseBean(it.statusCode, succeeded = true, requestUrl = it.requestUrl))
            } else {
                if (defGson == null) {
                    defGson = Gson()
                }
                try {
                    if (!it.message.isNullOrEmpty()) {
                        /*
                         * XkErrorResponse 的msg，表示服务错误，返回的 json 数据；
                         * 将其转回 BaseBean
                         */
//                        val trans = defGson?.fromJson<Any?>(it.message, BaseBean::class.java) ?: return@catch
//                        emit(BaseBean(trans.code, null, trans.msg, succeeded = false, requestUrl = it.requestUrl))

                    } else {
                        emit(BaseBean(it.statusCode, msg = "数据服务异常，请稍候重试", succeeded = false, requestUrl = it.requestUrl))
                    }
                } catch (e: Exception) {
                    emit(BaseBean(it.statusCode, succeeded = false, msg = it.message ?: e.message, requestUrl = it.requestUrl))
                }
            }
        } else { // 是否是 网络错误
            if (it is XkErrorThrowable) {
                val th = it.t
                if (th is ConnectException || th is SocketException || th is UnknownHostException || th is TimeoutException || th is SocketTimeoutException) {
                    emit(BaseBean(-1, succeeded = false, msg = th.message, netWorkErr = true, requestUrl = it.requestUrl))
                } else {
                    emit(BaseBean(-1, succeeded = false, msg = th.message, requestUrl = it.requestUrl))
                }
            }

        }
    }
}