package com.example.data.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val SHARED_PREFERENCE_KEY = "K_APP"
const val bookmarkIds = "bookmark_ids"

@Module
@InstallIn(SingletonComponent::class)
class LocalDataStoreModule {

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            SHARED_PREFERENCE_KEY,
            Context.MODE_PRIVATE,
        )
    }
}
