package com.example.kakaobankhomework.model

sealed class UiResult<out R> {
    object Loading : UiResult<Nothing>()
    data class Success<out T>(val data: T) : UiResult<T>()
    object Failure : UiResult<Nothing>()
}