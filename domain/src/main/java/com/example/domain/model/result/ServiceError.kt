package com.example.domain.model.result

sealed interface ServiceError {
    object NoNetwork : ServiceError
    object ResultNotFound : ServiceError
    data class MappingFail(val throwable: Throwable) : ServiceError
    data class Unknown(val throwable: Throwable) : ServiceError
}
