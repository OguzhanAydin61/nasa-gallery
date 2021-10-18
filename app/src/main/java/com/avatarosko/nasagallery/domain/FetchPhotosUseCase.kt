package com.avatarosko.nasagallery.domain

import com.avatarosko.nasagallery.common.AwesomeResult
import com.avatarosko.nasagallery.common.model.CameraTypes
import com.avatarosko.nasagallery.common.model.RoverTypes
import com.avatarosko.nasagallery.data.PhotoRepository
import com.avatarosko.nasagallery.data.model.ResponsePhotos
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class FetchPhotosUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) : BaseUseCase<FetchPhotosUseCase.Request, AwesomeResult<ResponsePhotos>>() {
    override fun execute(input: Request) = flow {
        emit(
            photoRepository
                .fetchDynamicPhotoList(
                    input.roverTypes,
                    input.page,
                    input.selectedCamera
                )

        )
    }

    data class Request(
        val roverTypes: RoverTypes,
        val page: Int,
        val selectedCamera: CameraTypes? = null
    )
}