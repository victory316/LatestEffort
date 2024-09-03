package com.choidev.latesteffort.feature.compose.ui.nestedscroll

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.choidev.core.actions.presenter.ActionPresenter

private const val nestedScrollRoute = "feature_nested_scroll"

fun NavGraphBuilder.nestedScrollScreen(presenter: ActionPresenter) {
    composable(
        route = nestedScrollRoute
    ) {
        NestedScrollScreen()
    }
}