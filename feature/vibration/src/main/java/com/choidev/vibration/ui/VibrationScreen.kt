package com.choidev.vibration.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.choidev.core.actions.VibrationAction
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.core.actions.presenter.SimpleActionPresenter
import com.choidev.latesteffort.core.util.vibration.VibrationManager
import com.choidev.vibration.VibrationViewModel
import java.security.PrivateKey
import javax.inject.Inject

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
            .padding(start = 16.dp, end = 16.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Column {
                Text(text = "진동 활성화")
                Switch(
                    checked = checked,
                    onCheckedChange = {
                        presenter.onClick(
                            VibrationAction.Vibrate(
                                activate = !checked,
                                duration = 1000L
                            )
                        )

                        viewModel.switchVibration(!checked)
                    },
                    modifier = Modifier.padding()
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
                                repeat = !vibrationState.value.repeat
                            )
                        )

                        viewModel.repeatVibration(!vibrationState.value.repeat)
                    },
                    modifier = Modifier.padding()
                )
            }

            Column {
                Text(text = "강도")
                Switch(
                    checked = checked,
                    onCheckedChange = {
                        presenter.onClick(
                            VibrationAction.Vibrate(
                                activate = !checked,
                                duration = 1000L
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
