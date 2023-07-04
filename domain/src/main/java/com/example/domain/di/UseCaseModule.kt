package com.example.domain.di

import com.example.domain.BookmarkUseCase
import com.example.domain.BookmarkUseCaseImpl
import com.example.domain.SearchUseCase
import com.example.domain.SearchUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindsBookmarkUseCase(
        bookmarkUseCaseImpl: BookmarkUseCaseImpl
    ): BookmarkUseCase

    @Binds
    fun bindsSearchUseCase(
        searchUseCaseImpl: SearchUseCaseImpl
    ): SearchUseCase
}