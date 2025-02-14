package com.rure.deepmedi.utils.di

import com.rure.deepmedi.data.repository.RetrofitApiRepositoryImpl
import com.rure.deepmedi.domain.repository.RetrofitApiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class BindModule {
    @Binds
    @ViewModelScoped
    abstract fun provideRetrofitRepository(impl: RetrofitApiRepositoryImpl): RetrofitApiRepository
}