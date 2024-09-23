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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
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
    var columnSize = 50.dp * state.sizeColumn
    var rowPosSize by remember { mutableStateOf(20.dp * state.posRow) }
    var columnPosSize by remember { mutableStateOf(20.dp * state.posColumn) }
    var boxZoom by remember { mutableStateOf(0f) }

    var offset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier
            .width(rowSize)
            .height(columnSize)
            .offset(x = rowPosSize, y =  columnPosSize)
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    boxZoom = maxOf(1f, boxZoom * zoom)
                    rowPosSize = centroid.x.dp
                    columnPosSize = centroid.y.dp
                }
            }
            .graphicsLayer {
                scaleX = boxZoom
                scaleY = boxZoom
            }
            .drawBehind {
                drawRect(color = Color.Green)
            }
    ) {
        Text(text = "Hello! I'm a new box")
    }
}

fun Offset.calculateNewOffset(
    centroid: Offset,
    pan: Offset,
    zoom: Float,
    gestureZoom: Float,
    size: IntSize
): Offset {
    val newScale = maxOf(1f, zoom * gestureZoom)
    val newOffset = (this + centroid / zoom) -
            (centroid / newScale + pan / zoom)
    return Offset(
        newOffset.x.coerceIn(0f, (size.width / zoom) * (zoom - 1f)),
        newOffset.y.coerceIn(0f, (size.height / zoom) * (zoom - 1f))
    )
}

