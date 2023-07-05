package com.example.network.model

import kotlinx.serialization.Serializable


@Serializable
data class VideoResultDto(
    val meta: MetaDto,
    val videos: List<VideoDto>?
)
