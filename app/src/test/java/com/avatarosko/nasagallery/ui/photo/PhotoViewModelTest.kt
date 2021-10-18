package com.avatarosko.nasagallery.ui.photo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.avatarosko.nasagallery.common.AwesomeResult
import com.avatarosko.nasagallery.common.model.RoverTypes
import com.avatarosko.nasagallery.common.network.NetworkConnectivityManager
import com.avatarosko.nasagallery.common.resource.ResourceWrapper
import com.avatarosko.nasagallery.data.model.ResponseError
import com.avatarosko.nasagallery.data.model.ResponseGeneralError
import com.avatarosko.nasagallery.data.model.ResponsePhoto
import com.avatarosko.nasagallery.data.model.ResponsePhotos
import com.avatarosko.nasagallery.domain.FetchPhotosUseCase
import com.avatarosko.nasagallery.utils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalStdlibApi
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class PhotoViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val testDispatcher
        get() = mainCoroutineRule.testDispatcher

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mFetchPhotosUseCase = mockk<FetchPhotosUseCase>(relaxed = true)
    private val mResourcesWrapper = mockk<ResourceWrapper>(relaxed = true)
    private val mNetworkConnectivityManager = mockk<NetworkConnectivityManager>()
    private val photoViewModel by lazy {
        PhotoViewModel(mFetchPhotosUseCase, mResourcesWrapper)
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        testDispatcher.cancel()
    }

    @Test
    fun givenResultSuccess_WhenFetchCuriosityRover_ThenReturnResponsePhotos() =
        testDispatcher.runBlockingTest {
            0
            // Given
            coEvery { mNetworkConnectivityManager.isNetworkAvailable } returns true
            coEvery {
                mFetchPhotosUseCase.execute(any())
            } returns flow<AwesomeResult<ResponsePhotos>> {
                emit(
                    AwesomeResult.Success(
                        ResponsePhotos(
                            listOf(
                                ResponsePhoto(
                                    "21321312"
                                )
                            )
                        )
                    )
                )
            }
            // When
            photoViewModel.fetchRoversPhoto(RoverTypes.Curiosity, 1)

            // Then
            launch {
                val photos = photoViewModel.photos.take(1).first()
                println(photos)
                assert(photos.isNotEmpty())
            }
        }

    @Test
    fun givenResultFail_WhenFetchWrongType_ThenReturnError() = testDispatcher.runBlockingTest {

        // Given
        coEvery { mNetworkConnectivityManager.isNetworkAvailable } returns true
        coEvery {
            mFetchPhotosUseCase.execute(any())
        } returns flow<AwesomeResult<ResponsePhotos>> {
            emit(
                AwesomeResult.ServerError(ResponseGeneralError(mError = ResponseError(mCode = "500")))
            )
        }

        // When
        photoViewModel.fetchRoversPhoto(RoverTypes.Spirit, 1)

        // Then
        launch {
            val photos = photoViewModel.errorState.take(1).first()
            println(photos)
        }
    }
}