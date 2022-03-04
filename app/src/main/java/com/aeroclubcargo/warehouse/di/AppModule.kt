package com.aeroclubcargo.warehouse.di

import dagger.hilt.android.qualifiers.ApplicationContext
import android.app.Application
import android.content.Context
import androidx.compose.runtime.currentCompositionLocalContext
import com.aeroclubcargo.warehouse.WarehouseApplication
import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.data.remote.ApiInterface
import com.aeroclubcargo.warehouse.data.repository.RepositoryImpl
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.aeroclubcargo.warehouse.data.local.DataStorePreferenceRepository
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCargoApi(): ApiInterface {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideCargoRepository(apiInterface: ApiInterface): Repository {
        return RepositoryImpl(apiInterface)
    }

    @Provides
    @Singleton
    fun getDataStorePreferenceRepository(@ApplicationContext appContext: Context) : DataStorePreferenceRepository{
        return DataStorePreferenceRepository(context = appContext)
    }


}