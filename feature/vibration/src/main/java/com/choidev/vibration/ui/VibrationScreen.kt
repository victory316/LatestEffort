package com.choidev.vibration.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.choidev.core.actions.SystemAction
import com.choidev.core.actions.VibrateAction
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.core.actions.presenter.SimpleActionPresenter
import com.choidev.latesteffort.core.design.compose.ScreenPaddingHorizontal
import com.choidev.vibration.R
import com.choidev.vibration.VibrationViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun VibrationScreen(
    presenter: ActionPresenter,
    viewModel: VibrationViewModel = hiltViewModel()
) {
    val vibrationState = viewModel.vibrationState.collectAsStateWithLifecycle()
    val openPatternDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.title_feature_vibration))
            })
        },
        modifier = Modifier
            .padding(horizontal = ScreenPaddingHorizontal())
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val effects = VibrateAction.VibrationEffect.values()

            Column {
                Text(
                    text = stringResource(id = R.string.title_vibration_effects),
                    style = MaterialTheme.typography.titleMedium
                )
                effects.forEach { effect ->
                    Row(
                        modifier = Modifier.clickable {
                            viewModel.selectVibrationEffect(effect)
                        }
                    ) {
                        RadioButton(
                            selected = vibrationState.value.effect == effect,
                            onClick = { viewModel.selectVibrationEffect(effect) }
                        )
                        Text(
                            text = effect.name,
                            modifier = Modifier.align(CenterVertically)
                        )
                    }
                }

                Button(
                    onClick = {
                        presenter.onClick(
                            VibrateAction.VibrateEffect(effect = vibrationState.value.effect)
                        )
                    }
                ) {
                    Text(text = stringResource(id = R.string.button_test))
                }
            }

            Divider()

            Text(
                text = stringResource(id = R.string.title_pattern_vibration_test),
                style = MaterialTheme.typography.titleMedium
            )

            if (vibrationState.value.patterns.isEmpty()) {
                Button(
                    onClick = { openPatternDialog.value = true }
                ) {
                    Text(text = stringResource(id = R.string.button_add_pattern))
                }
            } else {
                FlowRow(
                    modifier = Modifier.padding(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    vibrationState.value.patterns.forEach {
                        VibratePatternChip(
                            duration = it.first,
                            amplitude = it.second,
                            onClicked = { viewModel.removeVibrationPattern(it) }
                        )
                    }
                    ElevatedAssistChip(
                        onClick = { openPatternDialog.value = true },
                        label = { Icon(Icons.Rounded.Add, contentDescription = null) }
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.switch_activate_vibration_repeat),
                    modifier = Modifier.align(CenterVertically)
                )
                Switch(
                    checked = vibrationState.value.repeat,
                    onCheckedChange = {
                        viewModel.repeatVibration(!vibrationState.value.repeat)
                    },
                    modifier = Modifier.padding()
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                val errorMessage = stringResource(id = R.string.error_no_patterns)

                Text(
                    text = stringResource(id = R.string.button_test_pattern_vibration),
                    modifier = Modifier.align(CenterVertically)
                )
                IconButton(onClick = {
                    when {
                        vibrationState.value.patterns.isEmpty() -> {
                            presenter.onClick(
                                SystemAction.ShowToast(message = errorMessage)
                            )
                        }

                        vibrationState.value.activated -> {
                            presenter.onClick(VibrateAction.StopVibration)
                            viewModel.toggleVibrationPattern()
                        }

                        else -> {
                            presenter.onClick(
                                VibrateAction.VibratePattern(
                                    vibrationState.value.repeat,
                                    vibrationState.value.patterns
                                )
                            )
                            viewModel.toggleVibrationPattern()
                        }
                    }
                }) {
                    if (vibrationState.value.activated) {
                        Icon(Icons.Rounded.Close, contentDescription = null)
                    } else {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                    }
                }
            }
        }
    }

    if (openPatternDialog.value) {
        PatternInputDialog(
            onDismiss = {
                openPatternDialog.value = false
            },
            onConfirmed = {
                viewModel.addVibrationPattern(it)
                openPatternDialog.value = false
            }
        )
    }
}

@Composable
fun VibratePatternChip(
    duration: Int, amplitude: Int,
    onClicked: () -> Unit
) {
    ElevatedAssistChip(
        onClick = { onClicked.invoke() },
        label = { Text(text = "D : $duration A : $amplitude") }
    )
}

fun String.convertIntSafely(): Int {
    return if (this.isNotEmpty()) {
        this.toInt()
    } else {
        0
    }
}

@Preview
@Composable
fun PreviewVibrationScreen() {
    VibrationScreen(presenter = SimpleActionPresenter())
}
