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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagingNotificationDialog(
    onDismiss: () -> Unit,
    onConfirmed: (pattern: Pair<Int, Int>) -> Unit
) {
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
                    text = "알림 추가하기", style = MaterialTheme.typography.titleLarge
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "알림 시간", style = MaterialTheme.typography.titleSmall
                    )
                    OutlinedButton(onClick = { /*TODO*/ }) {
                        Text(text = "설정하기")
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "제목", style = MaterialTheme.typography.titleSmall
                    )
                    OutlinedTextField(value = "", onValueChange = {})
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "내용", style = MaterialTheme.typography.titleSmall
                    )
                    OutlinedTextField(value = "", onValueChange = {})
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "중요도 설정", style = MaterialTheme.typography.titleSmall
                    )
                    OutlinedButton(onClick = { /*TODO*/ }) {
                        Text(text = "URGENT")
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "잠금 해제 상태 필요", style = MaterialTheme.typography.titleSmall
                    )
                    Checkbox(checked = false, onCheckedChange = { })
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMessagingNotificationDialog() {
    LeTheme {
        NotificationDialog(onDismiss = { /*TODO*/ }, onConfirmed = {

        })
    }
}
