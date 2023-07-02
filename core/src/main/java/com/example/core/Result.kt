package com.example.core

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val serviceError: ServiceError) : Result<Nothing>
    object Loading : Result<Nothing>
}
