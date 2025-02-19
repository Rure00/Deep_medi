package com.rure.deepmedi.utils.di

import com.rure.deepmedi.data.retrofit.ApiService
import com.rure.deepmedi.data.retrofit.RetrofitClient
import com.rure.deepmedi.data.retrofit.UploadService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


@Module
@InstallIn(ViewModelComponent::class)
class ProvideModule {
    @Provides
    @ViewModelScoped
    fun provideApiService() : ApiService {
        return RetrofitClient.apiInstance.create(ApiService::class.java)
    }
    @Provides
    @ViewModelScoped
    fun provideUploadService() : UploadService {
        return RetrofitClient.uploadInstance.create(UploadService::class.java)
    }



    @Provides
    @ViewModelScoped
    fun provideIoDispatcher() : CoroutineDispatcher {
        return Dispatchers.IO
    }
}