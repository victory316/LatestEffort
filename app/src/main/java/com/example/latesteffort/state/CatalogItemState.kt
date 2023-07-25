package com.example.latesteffort.state

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.choidev.core.actions.NavigateAction

data class CatalogItemState(
    val title: String,
    val icon: ImageVector,
    val backgroundColor: Color = Color.LightGray,
    val action: NavigateAction
)
