package com.avatarosko.nasagallery.common.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.core.view.ViewCompat
import com.avatarosko.nasagallery.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class RoverTypes(val param: String, @DrawableRes val icon: Int, @IdRes val idRes: Int) :
    Parcelable {
    Curiosity("curiosity", R.drawable.ic_car_1, ViewCompat.generateViewId()),
    Opportunity("opportunity", R.drawable.ic_car_1, ViewCompat.generateViewId()),
    Spirit("spirit", R.drawable.ic_car_1, ViewCompat.generateViewId())
}
