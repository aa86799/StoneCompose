package com.stone.net

import android.content.Context
import com.stone.net.calladapter.FlowCallAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.net.ssl.X509TrustManager

class XkOkhttp(private val config: NetConfig) {

    private val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S")

    fun provideRetrofit(baseUrl: String, context: Context, isAsync: Boolean = true): Retrofit {
        return Retrofit.Builder()
                .client(provideOkhttpClient(context))
                .addCallAdapterFactory(FlowCallAdapterFactory.create(isAsync))
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
    }

    private fun provideOkhttpClient(context: Context): OkHttpClient {
        val builder = buildOkhttp(context)
        provideInterceptors().forEach {
            builder.addInterceptor(it)
        }
        provideNetworkInterceptors().forEach {
            builder.addNetworkInterceptor(it)
        }
        return builder.build()
    }

    private fun provideInterceptors(): MutableList<Interceptor> {
        val interceptors = mutableListOf<Interceptor>()
        interceptors.add(GzipRequestInterceptor())
        interceptors.add(Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                    .header("Cache-Control", "no-cache")
//                    .addHeader("Accept-Language", config.getAcceptLanguage())
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", config.getAuthorization() ?: "")
                    .build()
            chain.proceed(newRequest)
        })

        interceptors.add(HttpLoggingInterceptor().apply {
            level =  if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
        return interceptors
    }

    private fun provideNetworkInterceptors(): MutableList<Interceptor> {
        val interceptors = mutableListOf<Interceptor>()
        interceptors.add(Interceptor { chain ->
            val start = System.currentTimeMillis()
            val newRequest = chain.request().newBuilder().build()
            val response = chain.proceed(newRequest)
            val end = System.currentTimeMillis()
            val sb = StringBuilder()
            sb.append("startTime: ${sdf.format(Date(start))}；")
            sb.append("endTime: ${sdf.format(Date(end))}；")
            sb.append("consumeTime: ${end - start}毫秒；")
            sb.append("message: ${response.message()}。")
//            if (response.code() != 200) {
                config.aliLog(newRequest.url().toString(), sb.toString())
//            }
            return@Interceptor response
        })
        return interceptors
    }

    private fun buildOkhttp(context: Context): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(10, TimeUnit.SECONDS)
                .cache(Cache(context.applicationContext.cacheDir, 5 * 1024 * 1024))
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .hostnameVerifier { _, _ -> true }
                .sslSocketFactory(SSLSocketClient.sSLSocketFactory, object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    }

                    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return emptyArray()
                    }
                })
                .build()
        return builder
    }
}