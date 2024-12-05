package com.choidev.latesteffort.feature.compose.ui.image

import LeTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import com.supergene.loki.feature.motion.R

/**
 *  ref : https://gist.github.com/JolandaVerhoef/41bbacadead2ba3ce8014d67014efbdd
 */
@Composable
fun DownloadableImageViewer(
    pageItems: List<Int>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState {
        pageItems.size
    }

    Scaffold(
        topBar = {
            IconButton(onClick = { }) {
                Icons.Default.Close
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.align(Alignment.Center)
            ) { page ->
                TestImagePage(
                    imageResId = pageItems.getOrNull(page) ?: 0,
                )
            }
        }
    }
}

@Composable
fun TestImagePage(
    imageResId: Int,
    modifier: Modifier = Modifier
) {
    var offset by remember { mutableStateOf(Offset.Zero) }
    var zoom by remember { mutableStateOf(1f) }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TestImage(
            imageResId = imageResId,
            currentOffset = { offset },
            currentZoom = { zoom },
            onOffsetChanged = { offset = it },
            onZoomChanged = { zoom = it },
        )
    }
}

@Composable
fun TestImage(
    imageResId: Int,
    currentOffset: () -> Offset,
    currentZoom: () -> Float,
    onOffsetChanged: (offset: Offset) -> Unit,
    onZoomChanged: (zoom: Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = imageResId),
        contentDescription = null,
        modifier = modifier
            .pointerInput(Unit) {
                detectTransformGestures(
                    onGesture = { centroid, pan, gestureZoom, _ ->
                        onOffsetChanged(
                            currentOffset().calculateNewOffset(
                                centroid, pan, currentZoom(), gestureZoom, size
                            )
                        )
                        onZoomChanged(maxOf(1f, currentZoom() * gestureZoom))
                    }
                )
            }
            .graphicsLayer {
                translationX = -currentOffset().x * currentZoom()
                translationY = -currentOffset().y * currentZoom()
                scaleX = currentZoom()
                scaleY = currentZoom()
                transformOrigin = TransformOrigin(0f, 0f)
            }
    )
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

@Preview
@Composable
private fun PreviewDownloadableImageViewer() {
    LeTheme {
        DownloadableImageViewer(
            pageItems = listOf(
                R.drawable.img_sample
            )
        )
    }
}
