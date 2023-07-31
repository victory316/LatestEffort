package com.choidev.latesteffort.feature.notification_test.state

import com.choidev.core.actions.NotificationImportance

data class NotificationState(
    val title: String,
    val content: String,
    val importance: NotificationImportance
)
