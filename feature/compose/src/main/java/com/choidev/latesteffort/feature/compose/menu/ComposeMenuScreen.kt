package com.choidev.latesteffort.feature.compose.menu

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.latesteffort.feature.compose.navigation.composeMenuRoute
import com.choidev.latesteffort.feature.compose.ui.ComposeMenuScreen


fun NavGraphBuilder.composeMenuScreen(presenter: ActionPresenter) {
    composable(
        route = composeMenuRoute
    ) {
        ComposeMenuScreen(presenter)
    }
}
