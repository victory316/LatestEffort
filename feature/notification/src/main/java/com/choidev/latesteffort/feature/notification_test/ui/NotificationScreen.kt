package com.choidev.latesteffort.feature.notification_test.ui

import LeTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.core.actions.presenter.SimpleActionPresenter
import com.choidev.latesteffort.core.design.compose.ScreenPaddingHorizontal
import com.choidev.latesteffort.feature.notification_test.state.OnNewNotificationDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationTestScreen(presenter: ActionPresenter) {
    var newNotification: OnNewNotificationDialog? by remember { mutableStateOf(null) }

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
            Text(text = "알림 추가하기")
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Button(
                    onClick = { newNotification = OnNewNotificationDialog.BASIC }
                ) {
                    Text(text = "기본")
                }
                Button(
                    onClick = { newNotification = OnNewNotificationDialog.MESSAGING }
                ) {
                    Text(text = "메시지")
                }
                Button(
                    onClick = { newNotification = OnNewNotificationDialog.MEDIA }
                ) {
                    Text(text = "미디어")
                }
            }
            Divider()
            Text(text = "알림 목록")

            LazyColumn {
                items(10) {
                    NotificationItemUi()
                }
            }
        }
    }

    when (newNotification) {
        OnNewNotificationDialog.BASIC -> {
            NotificationDialog(onDismiss = { /*TODO*/ }, onConfirmed = {})
        }

        OnNewNotificationDialog.MEDIA -> {
            MessagingNotificationDialog(onDismiss = { /*TODO*/ }, onConfirmed = {})
        }

        OnNewNotificationDialog.MESSAGING -> {
            MessagingNotificationDialog(onDismiss = { /*TODO*/ }, onConfirmed = {})
        }

        null -> {
            /* no-op */
        }
    }
}

@Composable
fun NotificationItemUi() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "미디어")
        Text(text = "4분후 알림")
        Text(text = "중요도 높음")
    }
}

@Preview
@Composable
fun PreviewNotificationTestScreen() {
    LeTheme {
        NotificationTestScreen(presenter = SimpleActionPresenter())
    }
}
