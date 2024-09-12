package com.example.latesteffort.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.latesteffort.feature.compose.navigation.composeHomeScreen
import com.choidev.latesteffort.feature.notification_test.navigation.notificationScreen
import com.choidev.vibration.navigation.vibrationScreen
import com.supergene.loki.feature.motion.navigation.motionScreen

@Composable
fun LeNavHost(
    navController: NavHostController,
    presenter: ActionPresenter,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = catalogRoute,
        modifier = modifier
    ) {
        catalogScreen(presenter)
        vibrationScreen(presenter)
        notificationScreen(presenter)
        motionScreen(presenter)
        composeHomeScreen(presenter)
    }
}
