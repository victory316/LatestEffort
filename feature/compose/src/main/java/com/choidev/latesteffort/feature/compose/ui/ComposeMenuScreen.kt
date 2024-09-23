package com.choidev.latesteffort.feature.compose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.choidev.core.actions.NavigateAction
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.latesteffort.feature.compose.ui.custombrush.customBrushNavigation
import com.choidev.latesteffort.feature.compose.ui.customizable.customizableNavigation
import com.choidev.latesteffort.feature.compose.ui.flow.flowNavigationRoute
import com.choidev.latesteffort.feature.compose.ui.nestedscroll.nestedScrollRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeMenuScreen(
    presenter: ActionPresenter,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            Text(
                text = "Compose Playground",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(20.dp)
            )
        },
        modifier = Modifier.padding(horizontal = 16.dp)
    ) { padding ->
        Column(
            modifier = modifier.padding(padding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                onClick = {
                    presenter.onClick(NavigateAction.NavGraphDestination(nestedScrollRoute))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Nested Scroll",
                    modifier = Modifier.padding(10.dp)
                )
            }

            Card(
                onClick = {
                    presenter.onClick(NavigateAction.NavGraphDestination(flowNavigationRoute))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Flow Test",
                    modifier = Modifier.padding(10.dp)
                )
            }

            Card(
                onClick = {
                    presenter.onClick(NavigateAction.NavGraphDestination(customBrushNavigation))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Custom Brush",
                    modifier = Modifier.padding(10.dp)
                )
            }

            Card(
                onClick = {
                    presenter.onClick(NavigateAction.NavGraphDestination(customizableNavigation))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Sticky Movable Box",
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}
