package com.example.network.model

import java.util.Date

data class ImageDto(
    val collection: String,
    val thumbnailUrl: String,
    val imageUrl: String,
    val width: Int,
    val height: Int,
    val displaySitename: String,
    val docUrl: String,
    val dateTime: Date
)
