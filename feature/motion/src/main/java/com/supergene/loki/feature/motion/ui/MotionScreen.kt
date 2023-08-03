package com.supergene.loki.feature.motion.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
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
import com.choidev.latesteffort.core.design.compose.DividerPaddingVertical
import com.choidev.latesteffort.core.design.compose.ScreenPaddingHorizontal
import com.choidev.latesteffort.core.util.motion.AccelerometerData
import com.choidev.latesteffort.core.util.motion.CachedAccelerometerData
import com.choidev.latesteffort.core.util.motion.SensorRate
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.entryModelOf
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
    val fractionDigit by viewModel.fractionDigit.collectAsStateWithLifecycle()
    val cachedAccelerometerData by viewModel.cachedAccelerometerData.collectAsStateWithLifecycle()

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
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            OutlinedButton(
                onClick = { viewModel.incrementFractionDigit() },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = fractionDigit.toString(),
                    textAlign = TextAlign.Center
                )
            }
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

        Divider(modifier = Modifier.padding(vertical = DividerPaddingVertical()))

        Text(
            text = stringResource(id = R.string.title_shake_threshold),
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.label_sensitive),
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = stringResource(id = R.string.label_insensitive),
                style = MaterialTheme.typography.labelMedium
            )
        }
        Slider(
            value = shakeThreshold,
            onValueChange = { viewModel.shakeThreshold.value = it },
            valueRange = 0f..20f,
            steps = 10,
            modifier = Modifier.fillMaxWidth()
        )

        DynamicAccelerometerChart(cachedAccelerometerData)
    }

    if (openRateDialog) {
        RateDialog(
            currentRate = currentRate,
            onDismiss = { openRateDialog = false },
            onConfirmed = {
                viewModel.observeAccelerometer(rate = it)

                openRateDialog = false
            }
        )
    }
}

@Composable
fun DynamicAccelerometerChart(data: CachedAccelerometerData) {
    val chartEntryModel = entryModelOf(
        entriesOf(*data.accelerationX.toNumberPairs()),
        entriesOf(*data.accelerationY.toNumberPairs()),
        entriesOf(*data.accelerationZ.toNumberPairs()),
    )

    Chart(
        chart = lineChart(),
        model = chartEntryModel,
        startAxis = startAxis(),
        bottomAxis = bottomAxis(),
    )
}

fun List<Float>.toNumberPairs(): Array<Pair<Number, Number>> {
    return this.mapIndexed { index, value -> index to value }.toTypedArray()
}
