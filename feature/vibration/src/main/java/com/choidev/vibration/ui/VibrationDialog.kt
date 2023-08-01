package com.choidev.vibration.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.choidev.latesteffort.core.design.compose.AlertDialogPadding
import com.choidev.vibration.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatternInputDialog(
    onDismiss: () -> Unit,
    onConfirmed: (pattern: Pair<Int, Int>) -> Unit
) {
    var pattern by remember { mutableStateOf(Pair(0, 0)) }
    var durationError by remember { mutableStateOf(false) }
    var amplitudeError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = { onDismiss.invoke() },
        modifier = Modifier.padding(AlertDialogPadding())
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
                    text = stringResource(id = R.string.title_add_new_pattern),
                    style = MaterialTheme.typography.titleMedium
                )

                OutlinedTextField(
                    label = { Text(text = stringResource(id = R.string.input_duration)) },
                    value = pattern.first.toString(),
                    onValueChange = {
                        durationError = false
                        handlePatternInput(
                            operation = { pattern = pattern.copy(first = it.convertIntSafely()) },
                            onErrorOccurred = { durationError = true }
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    isError = durationError,
                    supportingText = {
                        if (durationError) {
                            Text(text = stringResource(id = R.string.error_invalid_input))
                        }
                    }
                )
                OutlinedTextField(
                    label = { Text(text = stringResource(id = R.string.input_amplitude)) },
                    value = pattern.second.toString(),
                    onValueChange = {
                        amplitudeError = false
                        handlePatternInput(
                            operation = { pattern = pattern.copy(second = it.convertIntSafely()) },
                            additionalCondition = { it.convertIntSafely() <= 255 },
                            onErrorOccurred = { amplitudeError = true }
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    isError = amplitudeError,
                    supportingText = {
                        if (amplitudeError) {
                            Text(text = stringResource(id = R.string.error_max_amplitude_reached))
                        }
                    }
                )

                Button(
                    onClick = { onConfirmed.invoke(pattern) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.button_add))
                }
            }
        }
    }
}

private fun handlePatternInput(
    operation: () -> Unit,
    additionalCondition: (() -> Boolean)? = null,
    onErrorOccurred: () -> Unit
) {
    try {
        additionalCondition?.let { check(it.invoke()) }
        operation.invoke()
    } catch (e: NumberFormatException) {
        onErrorOccurred.invoke()
    } catch (e: IllegalStateException) {
        onErrorOccurred.invoke()
    }
}
