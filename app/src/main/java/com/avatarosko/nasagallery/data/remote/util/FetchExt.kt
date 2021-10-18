package com.avatarosko.nasagallery.data.remote.util

import com.avatarosko.nasagallery.common.AwesomeResult
import com.avatarosko.nasagallery.data.model.ResponseGeneralError
import com.avatarosko.nasagallery.data.remote.NoConnectivityException
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <T> fetch(request: suspend () -> Response<T>): AwesomeResult<T> =
    withContext(Dispatchers.IO) {
        return@withContext try {
            val response = request.invoke()
            val errorResponse = response.errorBody()?.charStream()?.readText()
            return@withContext when {
                response.isSuccessful && response.body() != null -> AwesomeResult.Success(response.body()!!)
                errorResponse != null -> errorResponse.toServerError()
                else -> AwesomeResult.UnknownError(java.lang.Exception(response.message()))
            }
        } catch (e: Exception) {
            when (e) {
                is NoConnectivityException -> AwesomeResult.NoNetworkConnection
                else -> AwesomeResult.UnknownError(e)
            }
        }
    }

private fun String?.toServerError(): AwesomeResult.ServerError {
    val error = try {
        Gson().fromJson<ResponseGeneralError>(this, ResponseGeneralError::class.java)
    } catch (e: java.lang.Exception) {
        null
    }
    return AwesomeResult.ServerError(error ?: throw IllegalStateException())
}