package com.example.domain.model.result

sealed interface ServiceError {
    object NoNetwork : ServiceError
    object ResultNotFound : ServiceError
    data class Unknown(val throwable: Throwable) : ServiceError
}
