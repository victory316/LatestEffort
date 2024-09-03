package com.choidev.latesteffort.feature.compose.navigation

import LeTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.core.actions.presenter.SimpleActionPresenter
import com.choidev.latesteffort.feature.compose.ui.nestedscroll.NestedScrollScreen

@Composable
internal fun ComposeScreen(presenter: ActionPresenter) {
    NestedScrollScreen()
}

@Preview
@Composable
fun PreviewComposeScreen() {
    LeTheme {
        ComposeScreen(presenter = SimpleActionPresenter())
    }
}