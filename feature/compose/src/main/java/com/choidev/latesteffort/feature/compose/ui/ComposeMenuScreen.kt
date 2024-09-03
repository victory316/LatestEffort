package com.choidev.latesteffort.feature.compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.choidev.core.actions.NavigateAction
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.latesteffort.feature.compose.ui.nestedscroll.nestedScrollRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeMenuScreen(
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
    }
}