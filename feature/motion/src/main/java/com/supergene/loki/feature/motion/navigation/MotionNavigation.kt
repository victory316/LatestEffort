package com.supergene.loki.feature.motion.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.choidev.core.actions.presenter.ActionPresenter
import com.supergene.loki.feature.motion.ui.MotionTestScreen

const val motionRoute = "feature_motion_test"

fun NavController.navigateToMotion(navOptions: NavOptions? = null) {
    navigate(motionRoute, navOptions)
}

fun NavGraphBuilder.motionScreen(presenter: ActionPresenter) {
    composable(
        route = motionRoute
    ) {
        MotionTestScreen(presenter = presenter)
    }
}
