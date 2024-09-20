package com.choidev.latesteffort.feature.compose.ui.customizable.state

data class CustomizableScreenState(
    val currentSelected: BoxInfoState? = null,
    val boxes: List<BoxInfoState> = emptyList()
)
