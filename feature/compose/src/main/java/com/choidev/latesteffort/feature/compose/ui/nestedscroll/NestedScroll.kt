package com.choidev.latesteffort.feature.compose.ui.nestedscroll

import LeTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NestedScroll() {
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
        }
    }

    Box(modifier = Modifier.nestedScroll(nestedScrollConnection))
}

@Preview
@Composable
fun PreviewNestedScroll() {
    LeTheme {
        NestedScroll()
    }
}
