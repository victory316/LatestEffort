package com.example.data.repository.di

import com.example.data.repository.SearchRepositoryImpl
import com.example.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository
}