package com.avatarosko.nasagallery.data.model

import com.google.gson.annotations.SerializedName

data class ResponsePhotos(
    @SerializedName("photos")
    val mPhotos: List<ResponsePhoto>,
)
