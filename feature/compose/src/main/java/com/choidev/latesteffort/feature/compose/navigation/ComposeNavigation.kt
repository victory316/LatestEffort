package com.choidev.latesteffort.feature.compose.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.choidev.core.actions.presenter.ActionPresenter

const val composeRoute = "feature_compose"

fun NavController.navigateToCompose(navOptions: NavOptions? = null) {
    navigate(composeRoute, navOptions)
}

fun NavGraphBuilder.composeScreen(presenter: ActionPresenter) {
    composable(
        route = composeRoute
    ) {
        ComposeScreen(presenter)
    }
}