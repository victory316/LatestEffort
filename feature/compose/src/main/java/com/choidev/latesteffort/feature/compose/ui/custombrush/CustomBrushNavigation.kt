package com.choidev.latesteffort.feature.compose.ui.custombrush

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val customBrushNavigation = "feature_custom_brush_navigation"

fun NavGraphBuilder.customBrushScreen() {
    composable(
        route = customBrushNavigation
    ) {
        CustomBrushScreen()
    }
}
