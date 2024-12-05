package com.choidev.latesteffort.feature.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.latesteffort.feature.compose.menu.composeMenuScreen
import com.choidev.latesteffort.feature.compose.ui.custombrush.customBrushScreen
import com.choidev.latesteffort.feature.compose.ui.customizable.customizableScreen
import com.choidev.latesteffort.feature.compose.ui.flow.flowScreen
import com.choidev.latesteffort.feature.compose.ui.image.imageViewerScreen
import com.choidev.latesteffort.feature.compose.ui.nestedscroll.nestedScrollScreen

const val composeHomeRoute = "feature_compose"

@Composable
fun ComposeHomeNavHost(
    navController: NavHostController,
    presenter: ActionPresenter,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = composeMenuRoute,
        modifier = modifier
    ) {
        composeMenuScreen(presenter)
        nestedScrollScreen(presenter)
        flowScreen(presenter)
        customBrushScreen()
        customizableScreen(presenter)
        imageViewerScreen(presenter)
    }
}
