package com.example.kakaobankhomework.ui.model

sealed interface UiResult {
    object Loading : UiResult
    object Success : UiResult
    object Failure : UiResult
}