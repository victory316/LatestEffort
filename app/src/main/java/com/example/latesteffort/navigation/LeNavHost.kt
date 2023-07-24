package com.example.latesteffort.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.vibration.navigation.vibrationScreen

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
    }
}
