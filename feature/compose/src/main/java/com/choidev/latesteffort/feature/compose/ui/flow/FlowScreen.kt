package com.choidev.latesteffort.feature.compose.ui.flow

import LeTheme
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            Text(text = "Flow Test bed")
        }
    ) { padding ->
        FlowRow(modifier = Modifier.padding(padding)) {

        }
    }
}

@Preview
@Composable
fun PreviewFlowScreen() {
    LeTheme {
        FlowScreen()
    }
}
