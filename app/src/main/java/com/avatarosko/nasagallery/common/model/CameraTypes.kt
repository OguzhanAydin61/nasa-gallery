package com.avatarosko.nasagallery.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class CameraTypes(vararg val roverTypes: RoverTypes) : Parcelable {
    FHAZ(RoverTypes.Curiosity, RoverTypes.Opportunity, RoverTypes.Spirit),
    RHAZ(RoverTypes.Curiosity, RoverTypes.Opportunity, RoverTypes.Spirit),
    MAST(RoverTypes.Curiosity),
    CHEMCAM(RoverTypes.Curiosity),
    MAHLI(RoverTypes.Curiosity),
    MARDI(RoverTypes.Curiosity),
    NAVCAM(RoverTypes.Curiosity, RoverTypes.Opportunity, RoverTypes.Spirit),
    PANCAM(RoverTypes.Opportunity, RoverTypes.Spirit),
    MINITES(RoverTypes.Opportunity, RoverTypes.Spirit);

    companion object {
        fun getCameraTypes(roverTypes: RoverTypes): List<CameraTypes> {
            return CameraTypes.values().filter {
                it.roverTypes.contains(roverTypes)
            }
        }
    }
}
