package com.choidev.latesteffort.feature.compose.ui.customizable

import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
    val rowPosSize = 20.dp * state.posRow
    val columnPosSize = 20.dp * state.posColumn
    var boxZoom by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .offset(x = rowPosSize, y = columnPosSize)
            .width(rowSize)
            .height(columnSize)
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    Log.d("TAG", "ResizableBox: zoom : $zoom")
                    boxZoom = maxOf(1f, boxZoom * zoom)
                }
            }
            .graphicsLayer {
                scaleX = boxZoom
                scaleY = boxZoom
            }
            .drawBehind {
                drawRect(color = Color.Yellow)
            }
    ) {

    }
}
