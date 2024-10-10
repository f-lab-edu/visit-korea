package kr.ksw.visitkorea.data.remote.api

import android.util.Log
import kr.ksw.visitkorea.BuildConfig
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.internal.http2.ConnectionShutdownException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.jvm.Throws

class RetrofitInterceptor @Inject constructor() : Interceptor {

    @Throws(Exception::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestUrl = request.url
            .newBuilder()
            .apply {
                baseQueryMap.forEach { (key, value) ->
                    addQueryParameter(key, value)
                }
            }
            .build()
        Log.d("RetrofitInterceptor", requestUrl.toString())

        val newRequest = request
            .newBuilder()
            .url(requestUrl)
            .build()

        try {
            return chain.proceed(newRequest)
        } catch (e: Exception) {
            e.printStackTrace()
            val msg = when (e) {
                is SocketTimeoutException -> "Timeout - Please check your internet connection"
                is UnknownHostException -> "Unable to make a connection. Please check your internet"
                is ConnectionShutdownException -> "Connection shutdown. Please check your internet"
                is IOException -> "Server is unreachable, please try again later."
                is IllegalStateException -> "${e.message}"
                else -> "${e.message}"
            }
            return Response.Builder()
                .request(newRequest)
                .protocol(Protocol.HTTP_1_1)
                .code(999)
                .message(msg)
                .body("{${e}}".toResponseBody(null))
                .build()
        }
    }

    companion object {
        private val baseQueryMap = mapOf(
            "MobileOS" to "AND",
            "MobileApp" to "visitkorea",
            "_type" to "json",
            "serviceKey" to BuildConfig.API_KEY
        )
    }
}