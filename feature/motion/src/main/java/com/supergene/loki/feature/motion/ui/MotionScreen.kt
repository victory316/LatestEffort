package com.supergene.loki.feature.motion.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.latesteffort.core.design.compose.ScreenPaddingHorizontal
import com.choidev.latesteffort.core.util.motion.AccelerometerData
import com.choidev.latesteffort.core.util.motion.SensorRate
import com.supergene.loki.feature.motion.MotionViewModel
import com.supergene.loki.feature.motion.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MotionTestScreen(
    presenter: ActionPresenter,
    viewModel: MotionViewModel = hiltViewModel()
) {
    val accelerometerData by viewModel.accelerometerData.collectAsStateWithLifecycle()
    val currentRate by viewModel.currentRate.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.title_motion_test))
            })
        },
        modifier = Modifier
            .padding(horizontal = ScreenPaddingHorizontal())
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            AccelerometerUi(
                accelerometerData = accelerometerData,
                currentRate = currentRate,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun AccelerometerUi(
    accelerometerData: AccelerometerData,
    currentRate: SensorRate,
    viewModel: MotionViewModel,
    modifier: Modifier = Modifier
) {
    var openRateDialog by remember { mutableStateOf(false) }
    val shakeThreshold by viewModel.shakeThreshold.collectAsStateWithLifecycle()

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Accelerometer",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            OutlinedButton(
                onClick = { openRateDialog = true },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = currentRate.name,
                    textAlign = TextAlign.Center
                )
            }
        }
        Text(text = "GVT X : ${accelerometerData.gravityX}")
        Text(text = "GVT Y : ${accelerometerData.gravityY}")
        Text(text = "GVT Z : ${accelerometerData.gravityZ}")
        Text(text = "ACC X : ${accelerometerData.accelerationX}")
        Text(text = "ACC Y : ${accelerometerData.accelerationY}")
        Text(text = "ACC Z : ${accelerometerData.accelerationZ}")
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Shake threshold",
            style = MaterialTheme.typography.labelMedium
        )
        Slider(
            value = shakeThreshold,
            onValueChange = { viewModel.shakeThreshold.value = it },
            valueRange = 0f..20f,
            steps = 10,
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (openRateDialog) {
        RateDialog(
            onDismiss = { openRateDialog = false },
            onConfirmed = {
                viewModel.observeAccelerometer(rate = it)
                openRateDialog = false
            }
        )
    }
}
