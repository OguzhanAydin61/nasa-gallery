package com.avatarosko.nasagallery.common.resource

import androidx.annotation.StringRes

interface ResourceWrapper {
    fun getString(@StringRes idRes: Int): String
}