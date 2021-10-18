package com.avatarosko.nasagallery.data.remote.interceptors

import com.avatarosko.nasagallery.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class DefaultKeysInterceptor @Inject constructor() : Interceptor {
    companion object {
        const val QUERY_API_KEY = "api_key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val url = original.url.newBuilder()
            .addQueryParameter(QUERY_API_KEY, BuildConfig.API_KEY)
            .build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }

}