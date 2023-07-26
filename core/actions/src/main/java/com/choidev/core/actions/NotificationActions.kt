package com.choidev.core.actions

sealed interface NotificationAction : Action {

    data class BasicNotification(
        val title: String,
        val message: String
    ) : NotificationAction

    object MediaNotification : NotificationAction

    data class MessageNotification(
        val title: String,
        val message: String
    ) : NotificationAction
}
