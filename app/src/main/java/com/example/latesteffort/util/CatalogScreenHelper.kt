package com.example.latesteffort.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object CatalogScreenHelper {

    private val backgroundAlphaList = listOf(0.1f, 0.2f, 0.3f, 0.4f, 0.5f)

    @Composable
    fun getBackgroundColor(index: Int): Color {
        val alphaIndex = when {
            index > backgroundAlphaList.lastIndex -> {
                index - (index % backgroundAlphaList.size)
            }

            else -> {
                index
            }
        }

        return MaterialTheme.colorScheme.primary.copy(alpha = backgroundAlphaList[alphaIndex])
    }
}
