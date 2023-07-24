package com.example.latesteffort.model

import androidx.compose.material.icons.Icons
import com.choidev.core.actions.NavigateAction

data class CatalogMenu(
    val menuTitle: String,
    val menuIcon: Icons,
    val navAction: com.choidev.core.actions.NavigateAction,
)
