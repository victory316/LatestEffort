package com.example.network.model

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class ImageDto(
    val collection: String,
    val thumbnailUrl: String,
    val imageUrl: String,
    val width: Int,
    val height: Int,
    val displaySitename: String,
    val docUrl: String,
    val datetime: Date
)
