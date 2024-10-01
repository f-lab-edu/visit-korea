package kr.ksw.visitkorea.data.remote.api

import kr.ksw.visitkorea.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject



class RetrofitInterceptor @Inject constructor() : Interceptor {
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

        return chain.proceed(request
            .newBuilder()
            .url(requestUrl)
            .build()
        )
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