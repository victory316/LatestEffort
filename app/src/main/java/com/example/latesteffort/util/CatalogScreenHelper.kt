package com.example.latesteffort.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object CatalogScreenHelper {

    private val backgroundAlphaList = listOf(0.1f, 0.2f, 0.3f, 0.4f)
    private var currentIndex = 0

    @Composable
    fun getNextBackgroundColor(): Color {
        if (currentIndex == backgroundAlphaList.lastIndex) currentIndex = 0

        return MaterialTheme.colorScheme.primary.copy(alpha = backgroundAlphaList[++currentIndex])
    }
}
