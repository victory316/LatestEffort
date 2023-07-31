package com.choidev.core.actions

sealed interface NotificationAction : Action {

    data class BasicNotification(
        val title: String,
        val message: String,
        val importance: NotificationImportance = NotificationImportance.DEFAULT
    ) : NotificationAction

    object MediaNotification : NotificationAction

    data class MessageNotification(
        val title: String,
        val message: String,
        val importance: NotificationImportance = NotificationImportance.DEFAULT
    ) : NotificationAction
}

enum class NotificationImportance {
    DEFAULT,
    LOW,
    MIN,
    HIGH,
    MAX
}
