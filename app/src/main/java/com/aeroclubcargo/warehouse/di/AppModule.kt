package com.aeroclubcargo.warehouse.di

import android.content.Context
import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.common.Constants.BASE_URL
import com.aeroclubcargo.warehouse.data.local.DataStorePreferenceRepository
import com.aeroclubcargo.warehouse.data.remote.ApiInterface
import com.aeroclubcargo.warehouse.data.remote.TokenRefreshAPI
import com.aeroclubcargo.warehouse.data.repository.RepositoryImpl
import com.aeroclubcargo.warehouse.domain.repository.Repository
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getDataStorePreferenceRepository(@ApplicationContext appContext: Context): DataStorePreferenceRepository {
        return DataStorePreferenceRepository(context = appContext)
    }

    @Provides
    @Singleton
    fun getTokenRefreshAPI(): TokenRefreshAPI {
        val builder = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor()).build()
        return builder.client(client).build().create(TokenRefreshAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideCargoApi(
        dataStorePreferenceRepository: DataStorePreferenceRepository,
        tokenRefreshAPI: TokenRefreshAPI,
        @ApplicationContext appContext: Context
    ): ApiInterface {
        val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
        val client = OkHttpClient.Builder()
            .addInterceptor(
                AuthenticationInterceptorRefreshToken(
                    tokenRefreshAPI = tokenRefreshAPI,
                    dataStorePreferenceRepository = dataStorePreferenceRepository,
                    context = appContext
                )
            )
            .addNetworkInterceptor(StethoInterceptor()).build()
        return builder.client(client).build().create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideCargoRepository(
        apiInterface: ApiInterface,
        dataStorePreferenceRepository: DataStorePreferenceRepository
    ): Repository {
        return RepositoryImpl(apiInterface, dataStorePreferenceRepository)
    }


}