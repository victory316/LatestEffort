package com.choidev.latesteffort.feature.notification_test

import com.choidev.core.actions.NotificationAction

interface Notifier {

    fun buildNotification(action: NotificationAction)
}
