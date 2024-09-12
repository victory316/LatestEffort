package com.choidev.latesteffort.feature.compose.ui.flow

import LeTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            Text(text = "Flow Test bed")
        }
    ) { padding ->
        val itemModifier = Modifier
            .padding(4.dp)
            .height(80.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Cyan)

        FlowRow(modifier = Modifier.padding(padding)) {
            repeat(8) { item ->
                if ((item + 1) % 3 == 0) {
                    Spacer(modifier = itemModifier.fillMaxWidth())
                } else {
                    Spacer(modifier = itemModifier.weight(0.5f))
                }
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun PreviewFlowScreen() {
    LeTheme {
        FlowScreen()
    }
}
