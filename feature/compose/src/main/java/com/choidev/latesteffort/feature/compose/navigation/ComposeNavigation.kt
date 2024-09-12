package com.choidev.latesteffort.feature.compose.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.choidev.core.actions.presenter.ActionPresenter

const val composeMenuRoute = "feature_compose_menu"

fun NavController.navigateToCompose(navOptions: NavOptions? = null) {
    navigate(composeHomeRoute, navOptions)
}

fun NavGraphBuilder.composeHomeScreen(presenter: ActionPresenter) {
    composable(
        route = composeHomeRoute
    ) {
        ComposeHomeRoute(presenter)
    }
}