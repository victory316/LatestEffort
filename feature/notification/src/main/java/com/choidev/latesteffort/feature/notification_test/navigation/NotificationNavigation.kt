package com.choidev.latesteffort.feature.notification_test.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.latesteffort.feature.notification_test.ui.NotificationTestScreen

const val notificationRoute = "feature_notification_test"

fun NavController.navigateToNotification(navOptions: NavOptions? = null) {
    navigate(notificationRoute, navOptions)
}

fun NavGraphBuilder.notificationScreen(presenter: ActionPresenter) {
    composable(
        route = notificationRoute
    ) {
        NotificationTestScreen(presenter)
    }
}
