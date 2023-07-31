package com.supergene.loki.feature.motion.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.latesteffort.core.design.compose.ScreenPaddingHorizontal
import com.supergene.loki.feature.motion.MotionViewModel
import com.supergene.loki.feature.motion.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MotionTestScreen(
    presenter: ActionPresenter,
    viewModel: MotionViewModel = hiltViewModel()
) {
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

        }
    }
}
