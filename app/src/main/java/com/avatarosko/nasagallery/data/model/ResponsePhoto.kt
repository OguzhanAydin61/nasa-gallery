package com.avatarosko.nasagallery.data.model

import com.google.gson.annotations.SerializedName

data class ResponsePhoto(
    @SerializedName("id")
    val mId: String? = null,
    @SerializedName("sol")
    val mSol: Long? = null,
    @SerializedName("camera")
    val mCamera: Camera? = null,
    @SerializedName("earth_date")
    val mEarthDate: String? = null,
    @SerializedName("img_src")
    val mImgSrc: String? = null,
    @SerializedName("rover")
    val mRover: Rover? = null,
)

data class Camera(
    @SerializedName("id")
    val mId: String? = null,
    @SerializedName("name")
    val mName: String? = null,
    @SerializedName("rover_id")
    val mRoverId: String? = null,
    @SerializedName("full_name")
    val mFullName: String? = null
)

data class Rover(
    @SerializedName("id")
    val mId: String? = null,
    @SerializedName("name")
    val mName: String? = null,
    @SerializedName("status")
    val mStatus: String? = null,
    @SerializedName("landing_date")
    val mLandingDate: String? = null,
    @SerializedName("launch_date")
    val mLaunchDate: String? = null
)
