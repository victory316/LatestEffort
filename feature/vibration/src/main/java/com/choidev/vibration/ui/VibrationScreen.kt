package com.choidev.vibration.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.choidev.core.actions.VibrationAction
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.core.actions.presenter.SimpleActionPresenter
import com.choidev.latesteffort.core.design.compose.ScreenPaddingHorizontal
import com.choidev.vibration.VibrationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VibrationScreen(
    presenter: ActionPresenter,
    viewModel: VibrationViewModel = hiltViewModel()
) {
    val vibrationState = viewModel.vibrationState.collectAsStateWithLifecycle()
    val checked = vibrationState.value.activated

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

            Column {
                Text(text = "진동 세기 조절하기")
                Slider(
                    value = vibrationState.value.amplitude.toFloat(),
                    valueRange = 0f..255f,
                    onValueChange = { viewModel.vibrationAmplitude(it.toInt()) }
                )
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

            Divider()

            Column {
                Text(text = "강도")
                Switch(
                    checked = checked,
                    onCheckedChange = {
                        presenter.onClick(
                            VibrationAction.Vibrate(
                                activate = !checked,
                                duration = 1000L,
                                effect = vibrationState.value.effect,
                                amplitude = vibrationState.value.amplitude
                            )
                        )

                        viewModel.switchVibration(!checked)
                    },
                    modifier = Modifier.padding()
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewVibrationScreen() {
    VibrationScreen(presenter = SimpleActionPresenter())
}
