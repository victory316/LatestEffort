package com.choidev.latesteffort.feature.notification_test.ui

import LeTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.choidev.core.actions.NotificationAction
import com.choidev.core.actions.SystemAction
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.core.actions.presenter.SimpleActionPresenter
import com.choidev.latesteffort.core.design.compose.ScreenPaddingHorizontal
import com.choidev.latesteffort.feature.notification_test.NotificationViewModel
import com.choidev.latesteffort.feature.notification_test.state.OnNewNotificationDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationTestScreen(
    presenter: ActionPresenter,
    viewModel: NotificationViewModel = hiltViewModel()
) {
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
            Text(text = "알림 타입")
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
        }
    }

    when (newNotification) {
        OnNewNotificationDialog.BASIC -> {
            NotificationDialog(
                onDismiss = { newNotification = null },
                onConfirmed = {
                    presenter.onClick(
                        NotificationAction.BasicNotification(
                            title = "하하",
                            message = "호호"
                        )
                    )
                }
            )
        }

        OnNewNotificationDialog.MEDIA -> {
            presenter.onClick(SystemAction.ShowToast("미디어 알림을 띄웁니다."))
            viewModel.createNotification(NotificationAction.MediaNotification)
        }

        OnNewNotificationDialog.MESSAGING -> {
            MessagingNotificationDialog(
                onDismiss = { newNotification = null },
                onConfirmed = {
                    presenter.onClick(
                        NotificationAction.BasicNotification(
                            title = "하하",
                            message = "호호"
                        )
                    )
                }
            )
        }

        null -> {
            /* no-op */
        }
    }
}

@Preview
@Composable
fun PreviewNotificationTestScreen() {
    LeTheme {
        NotificationTestScreen(presenter = SimpleActionPresenter())
    }
}
