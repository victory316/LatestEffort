package com.example.data.repository.ext

import com.example.domain.model.result.Result
import com.example.domain.model.result.ServiceError
import retrofit2.Response

fun <T, R> Response<T>.mapResult(transform: (value: T) -> R): Result<R> {
    return if (this.isSuccessful) {
        this.body()?.let { body ->
            Result.Success(transform(body))
        } ?: Result.Failure(error = ServiceError.ResultNotFound)
    } else {
        Result.Failure(error = ServiceError.ResultNotFound)
    }
}
