package com.choidev.domain.catalog.di

import com.choidev.domain.catalog.CatalogUseCase
import com.choidev.domain.catalog.CatalogUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class CatalogModule {

    @Binds
    abstract fun bindsCatalogUseCase(
        catalogUseCaseImpl: CatalogUseCaseImpl
    ): CatalogUseCase
}
