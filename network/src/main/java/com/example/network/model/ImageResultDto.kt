package com.example.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageResultDto(
    val meta: MetaDto,
    val documents: List<ImageDto>?
)
