package com.example.domain.model.result

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Failure(val error: ServiceError) : Result<Nothing>
    object Loading : Result<Nothing>
}