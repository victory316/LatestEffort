package com.choidev.latesteffort.feature.compose.ui.customizable

import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.choidev.latesteffort.feature.compose.ui.customizable.state.BoxInfoState
import kotlin.math.absoluteValue

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
                PositionStickyBox(state = box)
            }
        }
    }
}

@Composable
fun PositionStickyBox(state: BoxInfoState) {
    val pxValue = with(LocalDensity.current) { 20.dp.toPx() }
    val rowSize = 50.dp * state.sizeRow
    var columnSize = 50.dp * state.sizeColumn
    var offsetX by remember { mutableFloatStateOf(pxValue * state.posRow) }
    var offsetY by remember { mutableFloatStateOf(pxValue * state.posColumn) }
    var boxZoom by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .width(rowSize)
            .height(columnSize)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.position.x.run {
                        if ((this - offsetX).absoluteValue.toDp() >= 50.dp) {
                            offsetX = this
                        }
                    }

                    change.position.y.run {
                        if ((this - offsetY).absoluteValue.toDp() >= 50.dp) {
                            offsetY = this
                        }
                    }
                }
            }
            .offset {
                IntOffset(x = offsetX.toInt(), y = offsetY.toInt())
            }
            .drawBehind {
                drawRect(Color.White)
            }
            .graphicsLayer {
                scaleX = boxZoom
                scaleY = boxZoom
            }
    ) {
        Text(text = "Hello! I'm a new box")
    }
}