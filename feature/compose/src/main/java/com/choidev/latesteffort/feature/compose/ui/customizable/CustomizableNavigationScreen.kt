package com.choidev.latesteffort.feature.compose.ui.customizable

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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

    Column(modifier = modifier.fillMaxSize()) {
        Row {
            Button(onClick = { viewModel.addBox() }) {
                Text(text = "Add new Box")
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f)
                .padding(20.dp)
        ) {
            screenState.boxes.forEach { box ->
                ResizableBox(state = box)
            }
        }
    }
}

@Composable
fun ResizableBox(state: BoxInfoState) {
    val rowSize = 50.dp * state.sizeRow
    val columnSize = 50.dp * state.sizeColumn

    Box(
        modifier = Modifier
            .width(rowSize)
            .height(columnSize)
            .drawBehind {
                drawRect(Color.Yellow)
            }
            .pointerInput(Unit) {

            }
    ) {

    }
}
