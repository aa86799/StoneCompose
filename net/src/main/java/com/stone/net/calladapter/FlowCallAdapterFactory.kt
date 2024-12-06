package com.stone.net.calladapter

import com.stone.net.calladapter.async.AsyncBodyFlowCallAdapter
import com.stone.net.calladapter.async.AsyncResponseFlowCallAdapter
import com.stone.net.calladapter.sync.BodyFlowCallAdapter
import com.stone.net.calladapter.sync.ResponseFlowCallAdapter
import kotlinx.coroutines.flow.Flow
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 适配 flow
 *
 * Api 接口方法返回值支持声明为以下类型，后面两种需要已添加GsonConverter。
 * ① Flow<ResponseBody>
 * ② Flow<Response<ResponseBody>>
 * ③ Flow<[Bean]>
 * ④ Flow<Response<[Bean]>>
 */
class FlowCallAdapterFactory private constructor(private val async: Boolean) : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (getRawType(returnType) != Flow::class.java) return null

        if (returnType !is ParameterizedType) {
            throw IllegalStateException("the flow type must be parameterized as Flow<Foo>!")
        }

        val flowableType = getParameterUpperBound(0, returnType)
        val rawFlowableType = getRawType(flowableType)

        return if (rawFlowableType == Response::class.java) {
            if (flowableType !is ParameterizedType) {
                throw IllegalStateException("the response type must be parameterized as Response<Foo>!")
            }
            val responseBodyType = getParameterUpperBound(0, flowableType)
            createResponseFlowCallAdapter(async, responseBodyType)
        } else {
            createBodyFlowCallAdapter(async, flowableType)
        }
    }

    companion object {
        @JvmStatic
        fun create(async: Boolean = false) = FlowCallAdapterFactory(async)
    }
}

private fun createResponseFlowCallAdapter(async: Boolean, responseBodyType: Type) =
        if (async)
            AsyncResponseFlowCallAdapter(responseBodyType)
        else
            ResponseFlowCallAdapter(responseBodyType)

private fun createBodyFlowCallAdapter(async: Boolean, responseBodyType: Type) =
        if (async)
            AsyncBodyFlowCallAdapter(responseBodyType)
        else
            BodyFlowCallAdapter(responseBodyType)