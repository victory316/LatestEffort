package com.choidev.vibration.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.latesteffort.core.util.vibration.VibrationManager
import java.security.PrivateKey
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VibrationScreen(
    presenter: ActionPresenter
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "진동 테스트") }) }
    ) { paddingValues ->

    }
}
