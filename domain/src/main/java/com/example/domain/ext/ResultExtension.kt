package com.example.domain.ext

fun <T, R> Result<T>.mapSuccess(transform: (value: T?) -> R): Result<R>? {
    return if (isSuccess) {
        Result.success(transform(this.getOrThrow()))
    } else {
        null
    }
}
