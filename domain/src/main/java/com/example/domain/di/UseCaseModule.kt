package com.example.domain.di

import com.example.domain.BookmarkUseCase
import com.example.domain.BookmarkUseCaseImpl
import com.example.domain.SearchUseCase
import com.example.domain.SearchUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindsBookmarkUseCase(
        bookmarkUseCase: BookmarkUseCaseImpl
    ): BookmarkUseCase

    @Binds
    fun bindsSearchUseCase(
        bookmarkUseCase: SearchUseCaseImpl
    ): SearchUseCase
}