package com.choidev.vibration.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.choidev.core.actions.VibrationAction
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.core.actions.presenter.SimpleActionPresenter
import com.choidev.latesteffort.core.design.compose.ScreenPaddingHorizontal
import com.choidev.vibration.VibrationViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun VibrationScreen(
    presenter: ActionPresenter,
    viewModel: VibrationViewModel = hiltViewModel()
) {
    val vibrationState = viewModel.vibrationState.collectAsStateWithLifecycle()
    val checked = vibrationState.value.activated
    val openPatternDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "진동 테스트") }) },
        modifier = Modifier
            .padding(horizontal = ScreenPaddingHorizontal())
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val effects = VibrationAction.VibrationEffect.values()

            Column {
                Text(
                    text = "Vibration effect 테스트",
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
                            VibrationAction.VibrateEffect(effect = vibrationState.value.effect)
                        )
                    }
                ) {
                    Text(text = "테스트 하기")
                }
            }

            Divider()

            Text(
                text = "패턴 진동 테스트",
                style = MaterialTheme.typography.titleMedium
            )

            if (vibrationState.value.patterns.isEmpty()) {
                Button(
                    onClick = { openPatternDialog.value = true }
                ) {
                    Text(text = "패턴 추가하기")
                }
            } else {
                FlowRow {
                    vibrationState.value.patterns.forEach {
                        VibratePatternChip(duration = it.first, amplitude = it.second)
                    }
                }

                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(Icons.Rounded.Add, contentDescription = null)
                }
            }

            Column {
                Text(text = "반복 활성화")
                Switch(
                    checked = vibrationState.value.repeat,
                    onCheckedChange = {
                        presenter.onClick(
                            VibrationAction.RepeatVibrate(
                                activate = !checked,
                                duration = 1000L,
                                repeat = !vibrationState.value.repeat,
                                effect = vibrationState.value.effect,
                                amplitude = vibrationState.value.amplitude
                            )
                        )

                        viewModel.repeatVibration(!vibrationState.value.repeat)
                    },
                    modifier = Modifier.padding()
                )
            }
        }
    }

    if (openPatternDialog.value) {
        PatternInputDialog { openPatternDialog.value = false }
    }
}

@Composable
fun VibratePatternChip(duration: Int, amplitude: Int) {
    Box {
        Row {
            Text(text = "D : $duration A : $amplitude")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatternInputDialog(onDismiss: () -> Unit) {
    var pattern by remember { mutableStateOf(Pair(0, 0)) }

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
                    text = "패턴을 새로 추가하세요.",
                    style = MaterialTheme.typography.titleMedium
                )

                Row {
                    Text(text = "Duration")
                    TextField(
                        value = pattern.first.toString(),
                        onValueChange = { pattern = pattern.copy(first = it.convertIntSafely()) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }
                Row {
                    Text(text = "Amplitude")
                    TextField(
                        value = pattern.second.toString(),
                        onValueChange = { pattern = pattern.copy(second = it.convertIntSafely()) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }

                Button(
                    onClick = { onDismiss.invoke() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "추가하기")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewVibrationScreen() {
    VibrationScreen(presenter = SimpleActionPresenter())
}

fun String.convertIntSafely(): Int {
    return if (this.isNotEmpty()) {
        this.toInt()
    } else {
        0
    }
}
