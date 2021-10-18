package com.avatarosko.nasagallery.data.remote

import com.avatarosko.nasagallery.data.model.ResponsePhotos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {
    @GET(UrlConst.Photo.DYNAMIC_PHOTO_LIST)
    suspend fun fetchDynamicPhotoList(
        @Path("roverType") roverType: String,
        @Query("page") page: Int = 1,
        @Query("sol") sol: Int = 1000,
        @Query("camera") camera: String? = null
    ): Response<ResponsePhotos>

}