package com.choidev.latesteffort.feature.compose.ui.nestedscroll

import LeTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.choidev.latesteffort.feature.compose.NestedScrollViewModel

/**
 *  ref: https://www.youtube.com/watch?v=JfYBCKRjFA0
 */
@Composable
internal fun NestedScrollScreen(
    viewModel: NestedScrollViewModel = hiltViewModel()
) {
    val minTopSectionSize = { 50f }
    val maxTopSectionSize = { 200f }
    var currentImgSize by remember { mutableStateOf(200f) }
    val dummyItems by viewModel.dummyItems.collectAsState()

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y.toInt()

                val newImgSize = currentImgSize + delta
                val previousImgSize = currentImgSize
                currentImgSize =
                    newImgSize.coerceIn(minTopSectionSize(), maxTopSectionSize())
                val consumed = currentImgSize - previousImgSize

                return Offset(0f, consumed)
            }
        }
    }

    Column(modifier = Modifier.nestedScroll(nestedScrollConnection)) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .size(currentImgSize.dp)
        ) {
            Text(text = "SUPERDUPERBIGONE")
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            items(dummyItems) {
                Card(modifier = Modifier.height(100.dp)) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillParentMaxSize()
                    ) {
                        Text(
                            text = it,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
internal fun PreviewNestedScrollScreen() {
    LeTheme {
        NestedScrollScreen()
    }
}
