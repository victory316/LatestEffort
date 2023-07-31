package com.example.latesteffort.di

import com.choidev.latesteffort.core.util.motion.MotionManager
import com.choidev.latesteffort.core.util.motion.MotionManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MotionManagerModule {

    @Binds
    abstract fun bindsMotionManager(
        motionManagerImpl: MotionManagerImpl
    ): MotionManager
}
