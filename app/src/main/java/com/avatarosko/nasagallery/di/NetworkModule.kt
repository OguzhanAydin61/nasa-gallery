package com.avatarosko.nasagallery.di

import android.content.Context
import com.avatarosko.nasagallery.App
import com.avatarosko.nasagallery.BuildConfig
import com.avatarosko.nasagallery.common.network.NetworkConnectivityManager
import com.avatarosko.nasagallery.common.network.NetworkConnectivityManagerImp
import com.avatarosko.nasagallery.common.resource.ResourceWrapper
import com.avatarosko.nasagallery.common.resource.ResourceWrapperImp
import com.avatarosko.nasagallery.data.remote.NetworkService
import com.avatarosko.nasagallery.data.remote.UrlConst
import com.avatarosko.nasagallery.data.remote.interceptors.DefaultKeysInterceptor
import com.avatarosko.nasagallery.data.remote.interceptors.NetworkConnectionInterceptor
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideNetworkConnectivityManager(@ApplicationContext context: Context): NetworkConnectivityManager =
        NetworkConnectivityManagerImp(context)

    @Provides
    @Singleton
    fun provideNetworkConnectionInterceptor(networkConnectivityManager: NetworkConnectivityManager) =
        NetworkConnectionInterceptor(networkConnectivityManager)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        networkConnectionInterceptor: NetworkConnectionInterceptor,
        defaultKeysInterceptor: DefaultKeysInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(defaultKeysInterceptor)
            .addInterceptor(networkConnectionInterceptor)
            .apply {
                if (BuildConfig.DEBUG)
                    addInterceptor(FlipperOkhttpInterceptor(App.networkFlipperPlugin))
            }
            .build()

    @Provides
    @Singleton
    fun provideConverterFactory() = GsonConverterFactory.create() as Converter.Factory

    @Provides
    @Singleton
    fun provideRetrofit(
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(UrlConst.BASE_URL) // BuildConfigField!
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .build()


    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit) = retrofit.create<NetworkService>()

    // I didn't want to create a different module, just for this one.
    @Singleton
    @Provides
    fun provideResourceWrapper(@ApplicationContext context: Context): ResourceWrapper =
        ResourceWrapperImp(context)
}