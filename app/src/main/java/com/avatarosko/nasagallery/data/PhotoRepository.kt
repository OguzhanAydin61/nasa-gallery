package com.avatarosko.nasagallery.data

import com.avatarosko.nasagallery.common.model.CameraTypes
import com.avatarosko.nasagallery.common.model.RoverTypes
import com.avatarosko.nasagallery.data.remote.RemoteDataSource
import javax.inject.Inject

class PhotoRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    suspend fun fetchDynamicPhotoList(
        roverType: RoverTypes,
        page: Int,
        cameraTypes: CameraTypes? = null
    ) = remoteDataSource.fetchDynamicPhotoList(roverType, page, cameraTypes)
}