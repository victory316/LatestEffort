package com.choidev.latesteffort.feature.notification_test.ui

import LeTheme
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.choidev.core.actions.NotificationAction
import com.choidev.core.actions.SystemAction
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.core.actions.presenter.SimpleActionPresenter
import com.choidev.latesteffort.core.design.compose.ScreenPaddingHorizontal
import com.choidev.latesteffort.feature.notification_test.NotificationViewModel
import com.choidev.latesteffort.feature.notification_test.state.OnNewNotificationDialog
import java.util.jar.Manifest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationTestScreen(
    presenter: ActionPresenter,
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val permission = android.Manifest.permission.POST_NOTIFICATIONS
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("dd", "isGranted")
            // Open camera
        } else {
            Log.d("dd", "isGranted false")
            // Show dialog
        }
    }

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
                    newNotification = null
                    viewModel.createNotification(
                        NotificationAction.BasicNotification(
                            title = it.first,
                            message = it.second
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
                    viewModel.createNotification(
                        NotificationAction.MessageNotification(
                            title = it.first,
                            message = it.second
                        )
                    )
                    newNotification = null
                }
            )
            checkAndRequestCameraPermission(context, permission, launcher)
        }

        null -> {
            /* no-op */
        }
    }
}

fun checkAndRequestCameraPermission(
    context: Context,
    permission: String,
    launcher: ManagedActivityResultLauncher<String, Boolean>
) {
    val permissionCheckResult = ContextCompat.checkSelfPermission(context, permission)
    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
        // Open camera because permission is already granted
    } else {
        // Request a permission
        launcher.launch(permission)
    }
}

@Preview
@Composable
fun PreviewNotificationTestScreen() {
    LeTheme {
        NotificationTestScreen(presenter = SimpleActionPresenter())
    }
}
