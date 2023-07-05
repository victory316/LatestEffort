package com.example.kakaobankhomework.action

sealed interface Action {
    data class ItemSaveChanged(val saved: Boolean) : Action
}