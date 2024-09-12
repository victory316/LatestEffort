package com.choidev.latesteffort.feature.compose.navigation

import LeTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.choidev.core.actions.Action
import com.choidev.core.actions.NavigateAction
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.core.actions.presenter.SimpleActionPresenter

@Composable
internal fun ComposeHomeRoute(presenter: ActionPresenter) {
    val nestedNavController = rememberNavController()

    val nestedPresenter = object : SimpleActionPresenter() {
        override fun onClick(action: Action) {
            when (action) {
                is NavigateAction.NavGraphDestination -> {
                    nestedNavController.navigate(action.destination)
                }

                else -> presenter.onClick(action)
            }
        }
    }

    ComposeHomeNavHost(
        navController = nestedNavController,
        presenter = nestedPresenter
    )
}

@Preview
@Composable
fun PreviewComposeScreen() {
    LeTheme {
        ComposeHomeRoute(presenter = SimpleActionPresenter())
    }
}