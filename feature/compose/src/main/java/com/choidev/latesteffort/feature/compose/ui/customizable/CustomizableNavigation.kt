package com.choidev.latesteffort.feature.compose.ui.customizable

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.choidev.core.actions.presenter.ActionPresenter

const val customizableNavigation = "feature_customizable_navigation"

fun NavGraphBuilder.customizableScreen(
    presenter: ActionPresenter
) {
    composable(
        route = customizableNavigation
    ) {
        CustomizableNavigationScreen(presenter)
    }
}