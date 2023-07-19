package com.choidev.latesteffort.feature.search_media.extension

import com.example.domain.model.result.Result

fun <T> Result<T>.getSuccess(): Result.Success<T>? {
    return (this as? Result.Success)
}
