package com.choidev.latesteffort.core.util.extension

fun <T> Result<T>.getSuccess(): Result.Success<T>? {
    return (this as? Result.Success)
}
