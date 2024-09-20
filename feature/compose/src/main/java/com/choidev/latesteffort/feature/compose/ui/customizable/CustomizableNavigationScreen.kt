package com.choidev.latesteffort.feature.compose.ui.customizable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
        ResizableBox(state = screenState.boxes.first())
    }
}

@Composable
fun ResizableBox(state: BoxInfoState) {
    val rowSize = 20.dp * state.sizeRow
    val columnSize = 20.dp * state.sizeColumn

    Box(
        modifier = Modifier
            .width(rowSize)
            .height(columnSize)
            .drawBehind {
                drawRect(Color.Yellow)
            }
    ) {

    }
}
