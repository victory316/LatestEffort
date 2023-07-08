package com.example.domain.model

import java.util.Date

data class Image(
    val thumbnailUrl: String,
    var bookmarked: Boolean,
    val page: Int,
    val dateTime: Date
)
