package com.avatarosko.nasagallery.data.remote.interceptors

import com.avatarosko.nasagallery.common.network.NetworkConnectivityManager
import com.avatarosko.nasagallery.data.remote.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor constructor(private val mNetworkConnectivityManager: NetworkConnectivityManager) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (mNetworkConnectivityManager.isNetworkAvailable.not()) {
            throw NoConnectivityException()
        }
        chain.request().newBuilder().also {
            return chain.proceed(it.build())
        }
    }

}