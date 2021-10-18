package com.avatarosko.nasagallery.data.model

import com.google.gson.annotations.SerializedName

data class ResponseGeneralError(
    @SerializedName("error")
    val mError: ResponseError? = null

)

data class ResponseError(
    @SerializedName("code")
    val mCode: String? = null,
    @SerializedName("message")
    val mMessage: String? = null
)
