package com.example.network.model

import java.util.Date

data class Video(
    val title: String,
    val url: String,
    val dateTime: Date,
    val playTime: String,
    val author: String
)
