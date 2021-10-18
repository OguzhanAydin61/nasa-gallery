package com.avatarosko.nasagallery.ui.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avatarosko.nasagallery.R
import com.avatarosko.nasagallery.common.AwesomeResult
import com.avatarosko.nasagallery.common.model.CameraTypes
import com.avatarosko.nasagallery.common.model.RoverTypes
import com.avatarosko.nasagallery.common.resource.ResourceWrapper
import com.avatarosko.nasagallery.data.model.ResponsePhoto
import com.avatarosko.nasagallery.domain.FetchPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val photosUseCase: FetchPhotosUseCase,
    private val mResourceWrapper: ResourceWrapper
) : ViewModel() {
    private val _photos = MutableStateFlow<List<ResponsePhoto>>(listOf())
    val photos = _photos as StateFlow<List<ResponsePhoto>>

    private val _errorState = MutableStateFlow<PhotoListViewState>(PhotoListViewState())
    val errorState = _errorState as StateFlow<PhotoListViewState>


    var selectedFilter: CameraTypes? = null
    fun fetchRoversPhoto(roverType: RoverTypes, page: Int) {
        photosUseCase.execute(FetchPhotosUseCase.Request(roverType, page, selectedFilter))
            .onEach {
                when (it) {
                    is AwesomeResult.Loading -> _errorState.value =
                        PhotoListViewState(isLoading = it.isLoading)
                    AwesomeResult.NoNetworkConnection -> _errorState.value =
                        PhotoListViewState(errorMessage = "No Internet")
                    is AwesomeResult.ServerError -> {
                        _errorState.value =
                            PhotoListViewState(errorMessage = it.errorData.mError?.mMessage.orEmpty())
                    }
                    is AwesomeResult.Success -> {
                        if (page == 1)
                            if (it.data.mPhotos.isEmpty()) {
                                _errorState.value =
                                    PhotoListViewState(errorMessage = mResourceWrapper.getString(R.string.no_internet))
                            } else {
                                _errorState.value = PhotoListViewState(errorMessage = "")
                                _photos.value = it.data.mPhotos
                            }
                        else
                            _photos.value = _photos.value.plus(it.data.mPhotos)
                    }
                    is AwesomeResult.UnknownError -> _errorState.value =
                        PhotoListViewState(errorMessage = mResourceWrapper.getString(R.string.unknown_error))
                }
            }
            .launchIn(viewModelScope)
    }


}