package com.choidev.latesteffort.feature.compose.ui

import LeTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.choidev.core.actions.NavigateAction
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.core.actions.presenter.SimpleActionPresenter
import com.choidev.latesteffort.feature.compose.ui.flow.flowNavigationRoute
import com.choidev.latesteffort.feature.compose.ui.nestedscroll.nestedScrollRoute

@Composable
fun ComposeHomeScreen(
    presenter: ActionPresenter,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Card(
            onClick = {
                presenter.onClick(NavigateAction.NavGraphDestination(nestedScrollRoute))
            }
        ) {
            Text(text = "Nested Scroll")
        }

        Card(
            onClick = {
                presenter.onClick(NavigateAction.NavGraphDestination(flowNavigationRoute))
            }
        ) {
            Text(text = "Flow Test")
        }
    }
}

@Preview
@Composable
fun PreviewComposeHomeScreen() {
    LeTheme {
        ComposeHomeScreen(presenter = SimpleActionPresenter())
    }
}