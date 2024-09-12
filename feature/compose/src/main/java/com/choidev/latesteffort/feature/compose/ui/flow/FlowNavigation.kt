package com.choidev.latesteffort.feature.compose.ui.flow

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.choidev.core.actions.presenter.ActionPresenter

const val flowNavigationRoute = "feature_compose_flow_navigation"

fun NavGraphBuilder.flowScreen(presenter: ActionPresenter) {
    composable(
        route = flowNavigationRoute
    ) {
        FlowScreen()
    }
}