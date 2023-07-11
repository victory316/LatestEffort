package com.example.domain.model.result

sealed interface ServiceError {
    object SearchFail : ServiceError
    object ResultNotFound : ServiceError

    object PagingFail : ServiceError
    data class MappingFail(val throwable: Throwable) : ServiceError
    data class Unknown(val throwable: Throwable) : ServiceError
}
