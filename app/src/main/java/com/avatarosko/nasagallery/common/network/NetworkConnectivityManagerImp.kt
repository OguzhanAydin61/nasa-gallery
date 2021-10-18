package com.avatarosko.nasagallery.common.network

import android.content.Context
import com.avatarosko.nasagallery.common.ext.isNetworkAvailable

class NetworkConnectivityManagerImp(private val context: Context) : NetworkConnectivityManager {
    override val isNetworkAvailable: Boolean
        get() = context.isNetworkAvailable()
}