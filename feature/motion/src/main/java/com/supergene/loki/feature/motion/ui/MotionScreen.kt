package com.supergene.loki.feature.motion.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.latesteffort.core.design.compose.ScreenPaddingHorizontal
import com.choidev.latesteffort.core.util.motion.AccelerometerData
import com.supergene.loki.feature.motion.MotionViewModel
import com.supergene.loki.feature.motion.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MotionTestScreen(
    presenter: ActionPresenter,
    viewModel: MotionViewModel = hiltViewModel()
) {
    val accelerometerData by viewModel.accelerometerData.collectAsStateWithLifecycle()

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
            AccelerometerUi(accelerometerData)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccelerometerUi(
    accelerometerData: AccelerometerData,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        Text(
            text = "Accelerometer",
            style = MaterialTheme.typography.titleMedium
        )
        Text(text = "GVT X : ${accelerometerData.gravityX}")
        Text(text = "GVT Y : ${accelerometerData.gravityY}")
        Text(text = "GVT Z : ${accelerometerData.gravityZ}")
        Text(text = "ACC X : ${accelerometerData.accelerationX}")
        Text(text = "ACC Y : ${accelerometerData.accelerationY}")
        Text(text = "ACC Z : ${accelerometerData.accelerationZ}")
    }
}
