package com.example.network.model

import kotlinx.serialization.Serializable
import java.util.Date


@Serializable
data class VideoDto(
    val title: String,
    val url: String,
    val dateTime: Date,
    val playTime: String,
    val thumbnail: String,
    val author: String
)
