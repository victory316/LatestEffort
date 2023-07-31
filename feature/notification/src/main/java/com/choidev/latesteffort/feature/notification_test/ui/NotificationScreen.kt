package com.choidev.latesteffort.feature.notification_test.ui

import LeTheme
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
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
import com.choidev.latesteffort.feature.notification_test.R
import com.choidev.latesteffort.feature.notification_test.state.OnNewNotificationDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationTestScreen(
    presenter: ActionPresenter,
    viewModel: NotificationViewModel = hiltViewModel()
) {

    var newNotification: OnNewNotificationDialog? by remember { mutableStateOf(null) }
    val context = LocalContext.current

    val permission = android.Manifest.permission.POST_NOTIFICATIONS
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            presenter.onClick(
                SystemAction.ShowToast(context.getString(R.string.toast_need_notification_permission))
            )
            newNotification = null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.title_notification_test))
            })
        },
        modifier = Modifier
            .padding(horizontal = ScreenPaddingHorizontal())
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = stringResource(id = R.string.title_notification_type))
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Button(
                    onClick = { newNotification = OnNewNotificationDialog.BASIC }
                ) {
                    Text(text = stringResource(id = R.string.btn_basic_notification))
                }
                Button(
                    onClick = { newNotification = OnNewNotificationDialog.MESSAGING }
                ) {
                    Text(text = stringResource(id = R.string.btn_message_notification))
                }
                Button(
                    onClick = { newNotification = OnNewNotificationDialog.MEDIA }
                ) {
                    Text(text = stringResource(id = R.string.btn_media_notification))
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
                            title = it.title,
                            message = it.content,
                            importance = it.importance
                        )
                    )
                }
            )
            requestPermissionIfNeeded(context, permission, launcher)
        }

        OnNewNotificationDialog.MEDIA -> {
            presenter.onClick(
                SystemAction.ShowToast(
                    stringResource(id = R.string.toast_showing_media_notification)
                )
            )
            viewModel.createNotification(NotificationAction.MediaNotification)
            requestPermissionIfNeeded(context, permission, launcher)
        }

        OnNewNotificationDialog.MESSAGING -> {
            presenter.onClick(
                SystemAction.ShowToast(
                    stringResource(id = R.string.toast_showing_media_notification)
                )
            )
            viewModel.createNotification(NotificationAction.MessageNotification)
            requestPermissionIfNeeded(context, permission, launcher)
        }

        null -> {
            /* no-op */
        }
    }
}

fun requestPermissionIfNeeded(
    context: Context,
    permission: String,
    launcher: ManagedActivityResultLauncher<String, Boolean>
) {
    val permissionCheckResult = ContextCompat.checkSelfPermission(context, permission)
    if (permissionCheckResult != PackageManager.PERMISSION_GRANTED) {
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
