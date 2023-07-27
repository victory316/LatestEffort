package com.choidev.latesteffort.feature.notification_test.di

import com.choidev.latesteffort.feature.notification_test.Notifier
import com.choidev.latesteffort.feature.notification_test.NotifierImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class NotifierModule {

    @Binds
    abstract fun bindsNotifier(
        notifier: NotifierImpl
    ): Notifier
}
