package com.choidev.latesteffort.feature.compose.ui.customizable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.choidev.latesteffort.feature.compose.ui.customizable.state.BoxInfoState

@Composable
fun CustomizableNavigationScreen(
    modifier: Modifier = Modifier,
    viewModel: CustomizableViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {
    }
}

@Composable
fun ResizableBox(state: BoxInfoState) {

}
