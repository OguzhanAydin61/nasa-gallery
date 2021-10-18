package com.avatarosko.nasagallery.data.remote

import com.avatarosko.nasagallery.common.AwesomeResult
import com.avatarosko.nasagallery.common.model.CameraTypes
import com.avatarosko.nasagallery.common.model.RoverTypes
import com.avatarosko.nasagallery.data.model.ResponsePhotos
import com.avatarosko.nasagallery.data.remote.util.fetch
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val networkService: NetworkService) {
    suspend fun fetchDynamicPhotoList(
        roverType: RoverTypes,
        page: Int,
        cameraTypes: CameraTypes?
    ): AwesomeResult<ResponsePhotos> {
        return fetch {
            networkService.fetchDynamicPhotoList(
                roverType.param,
                camera = cameraTypes?.name,
                page = page
            )
        }
    }
}