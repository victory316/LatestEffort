package com.choidev.latesteffort.feature.notification_test.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.choidev.core.actions.presenter.ActionPresenter

const val notificationTestRoute = "feature_notification_test"

fun NavController.navigateToNotification(navOptions: NavOptions? = null) {
    navigate(notificationTestRoute, navOptions)
}

fun NavGraphBuilder.notificationTestScreen(presenter: ActionPresenter) {
    composable(
        route = notificationTestRoute
    ) {

    }
}
