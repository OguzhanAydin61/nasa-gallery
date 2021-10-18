package com.avatarosko.nasagallery.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterAdapterModel(val cameraTypes: CameraTypes, var isChecked: Boolean = false) :
    Parcelable