package com.supergene.loki.feature.motion.ui

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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.choidev.latesteffort.core.util.motion.SensorRate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RateDialog(
    currentRate: SensorRate = SensorRate.NORMAL,
    onDismiss: () -> Unit,
    onConfirmed: (rate: SensorRate) -> Unit
) {
    var selectedRate by remember { mutableStateOf(currentRate) }
    val sensorRate = SensorRate.values()

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
                    text = "감도 선택",
                    style = MaterialTheme.typography.titleMedium
                )

                sensorRate.forEach { rate ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        RadioButton(
                            selected = selectedRate == rate,
                            onClick = { selectedRate = rate }
                        )
                        Text(
                            text = rate.name,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }

                Button(
                    onClick = { onConfirmed.invoke(selectedRate) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "완료")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewRateDialog() {
    LeTheme {
        RateDialog(onDismiss = {}, onConfirmed = {})
    }
}
