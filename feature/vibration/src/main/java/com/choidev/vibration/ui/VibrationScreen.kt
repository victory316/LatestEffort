package com.choidev.vibration.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.choidev.core.actions.VibrationAction
import com.choidev.core.actions.presenter.ActionPresenter
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
        Row(
            modifier = Modifier.padding(paddingValues)
        ) {
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
