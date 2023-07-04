package com.example.network.model

import kotlinx.serialization.Serializable


@Serializable
data class MetaDto(
    val totalCount: Int,
    val pageableCount: Int,
    val isEnd: Boolean,
)
