package com.example.latesteffort.di

import com.choidev.domain.catalog.CatalogRepository
import com.choidev.latesteffort.data.catalog.repository.CatalogRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CatalogRepositoryModule {

    @Binds
    abstract fun bindsCatalogRepositoryModule(
        repository: CatalogRepositoryImpl
    ): CatalogRepository
}
