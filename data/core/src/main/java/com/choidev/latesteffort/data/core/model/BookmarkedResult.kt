package com.choidev.latesteffort.data.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "bookmarked_result"
)
data class BookmarkedResult(
    @PrimaryKey
    val id: String,
    val url: String,
    val thumbnailUrl: String,
)
