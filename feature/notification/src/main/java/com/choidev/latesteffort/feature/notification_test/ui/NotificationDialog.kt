package com.choidev.latesteffort.feature.notification_test.ui

import LeTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationDialog(
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
