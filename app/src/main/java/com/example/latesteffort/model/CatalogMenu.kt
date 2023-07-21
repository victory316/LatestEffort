package com.example.latesteffort.model

import androidx.compose.material.icons.Icons
import com.example.latesteffort.action.NavigateAction

data class CatalogMenu(
    val menuTitle: String,
    val menuIcon: Icons,
    val navAction: NavigateAction,
)
