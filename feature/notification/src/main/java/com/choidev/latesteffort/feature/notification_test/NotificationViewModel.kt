package com.choidev.latesteffort.feature.notification_test

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notifier: Notifier
) : ViewModel() {

    fun createNotification() {
        notifier.buildNotification()
    }
}
