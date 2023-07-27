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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaNotificationDialog(
    onDismiss: () -> Unit,
    onConfirmed: (pattern: Pair<String, String>) -> Unit
) {
    var message by remember { mutableStateOf(Pair("", "")) }

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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "제목", style = MaterialTheme.typography.titleSmall
                    )
                    OutlinedTextField(value = "", onValueChange = {
                        message = message.copy(first = it)
                    })
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "내용", style = MaterialTheme.typography.titleSmall
                    )
                    OutlinedTextField(value = "", onValueChange = {
                        message = message.copy(second = it)
                    })
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

                Button(
                    onClick = { }
                ) {
                    Text(text = "완료")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMediaNotificationDialog() {
    LeTheme {
        NotificationDialog(onDismiss = { /*TODO*/ }, onConfirmed = {

        })
    }
}
