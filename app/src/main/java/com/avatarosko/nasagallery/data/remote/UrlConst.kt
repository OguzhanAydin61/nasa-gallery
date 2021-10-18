package com.avatarosko.nasagallery.data.remote

import com.avatarosko.nasagallery.BuildConfig

object UrlConst {
    const val BASE_URL = BuildConfig.BASE_URL

    object Photo {
        private const val PHOTOS_PREFIX = "v1/rovers/"
        const val DYNAMIC_PHOTO_LIST = PHOTOS_PREFIX + "{roverType}/photos/"
    }
}