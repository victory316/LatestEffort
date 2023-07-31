package com.choidev.latesteffort.feature.notification_test.ui

import LeTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.choidev.core.actions.NotificationImportance
import com.choidev.latesteffort.feature.notification_test.R
import com.choidev.latesteffort.feature.notification_test.state.NotificationState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationDialog(
    onDismiss: () -> Unit,
    onConfirmed: (state: NotificationState) -> Unit
) {
    var state by remember {
        mutableStateOf(
            NotificationState(
                title = "",
                content = "",
                importance = NotificationImportance.DEFAULT
            )
        )
    }

    AlertDialog(
        onDismissRequest = { onDismiss.invoke() },
    ) {
        Surface(
            modifier = Modifier
                .wrapContentSize(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.dialog_title_create_notification),
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    OutlinedTextField(
                        label = {
                            Text(text = stringResource(id = R.string.dialog_notification_title))
                        },
                        value = state.title,
                        onValueChange = { state = state.copy(title = it) }
                    )
                }

                Row(
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    OutlinedTextField(
                        label = {
                            Text(text = stringResource(id = R.string.dialog_notification_contents))
                        },
                        value = state.content,
                        onValueChange = { state = state.copy(content = it) }
                    )
                }

                val importanceSettings = NotificationImportance.values()

                importanceSettings.forEach { importance ->
                    Row(
                        verticalAlignment = CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        RadioButton(
                            selected = state.importance == importance,
                            onClick = { state = state.copy(importance = importance) }
                        )
                        Text(
                            text = importance.name,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(CenterVertically)
                        )
                    }
                }

                Button(
                    onClick = {
                        onConfirmed.invoke(state)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.dialog_notification_done))
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewNotificationDialog() {
    LeTheme {
        NotificationDialog(onDismiss = { /*TODO*/ }, onConfirmed = {

        })
    }
}
