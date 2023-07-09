package com.example.kakaobankhomework.util

import com.example.domain.model.result.Result

fun <T> Result<T>.getSuccess(): Result.Success<T>? {
    return (this as? Result.Success)
}
