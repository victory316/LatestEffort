package com.example.domain.model

import java.util.Date

data class Video(
    val thumbnailUrl: String,
    val bookmarked: Boolean,
    val page: Int,
    val dateTime: Date
)
