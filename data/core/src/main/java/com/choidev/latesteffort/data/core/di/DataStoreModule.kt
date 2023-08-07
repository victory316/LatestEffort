package com.choidev.latesteffort.data.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")
val PREF_KEY_MENU_TYPE_INDEX = intPreferencesKey("PREF_KEY_MENU_TYPE_INDEX")

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    fun providesAppPreferencesDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.datastore
}