package com.example.latesteffort.di

import android.content.Context
import android.os.VibratorManager
import com.choidev.latesteffort.core.util.vibration.VibrationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Provides
    fun provideVibrationManager(
        @ApplicationContext context: Context
    ) {
        return VibrationManager(context)
    }
}