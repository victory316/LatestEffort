package com.example.latesteffort.state

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import com.choidev.core.actions.NavigateAction

data class CatalogItemState(
    val title: String,
    val icon: ImageVector? = null,
    val painter: Painter? = null,
    val backgroundColor: Color = Color.LightGray,
    val action: NavigateAction
)
