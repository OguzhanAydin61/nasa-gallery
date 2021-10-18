package com.avatarosko.nasagallery.common

import com.avatarosko.nasagallery.data.model.ResponseGeneralError

sealed class AwesomeResult<out S> {
    data class Success<out S>(val data: S) : AwesomeResult<S>()
    data class Loading(val isLoading: Boolean = true) : AwesomeResult<Nothing>()
    data class UnknownError(val exception: Throwable) : AwesomeResult<Nothing>()
    data class ServerError(val errorData: ResponseGeneralError) : AwesomeResult<Nothing>()
    object NoNetworkConnection : AwesomeResult<Nothing>()
}
