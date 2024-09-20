package com.choidev.latesteffort.feature.compose.ui.customizable

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val customizableNavigation = "feature_customizable_navigation"

fun NavGraphBuilder.customizableScreen() {
    composable(
        route = customizableNavigation
    ) {
        CustomizableNavigationScreen()
    }
}