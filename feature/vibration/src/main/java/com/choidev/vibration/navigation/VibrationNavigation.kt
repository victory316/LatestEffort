package com.choidev.vibration.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.vibration.ui.VibrationScreen

const val vibrationRoute = "feature_vibration"

fun NavController.navigateToVibration(navOptions: NavOptions? = null) {
    navigate(vibrationRoute, navOptions)
}

fun NavGraphBuilder.vibrationScreen(presenter: ActionPresenter) {
    composable(
        route = vibrationRoute
    ) {
        VibrationScreen(presenter)
    }
}
