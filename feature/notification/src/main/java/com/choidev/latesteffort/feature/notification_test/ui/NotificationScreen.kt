package com.choidev.latesteffort.feature.notification_test.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.latesteffort.core.design.compose.ScreenPaddingHorizontal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationTestScreen(presenter: ActionPresenter) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "알림 테스트")
            })
        },
        modifier = Modifier
            .padding(horizontal = ScreenPaddingHorizontal())
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
        }
    }
}
