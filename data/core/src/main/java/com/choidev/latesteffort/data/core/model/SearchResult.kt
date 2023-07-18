package com.choidev.latesteffort.data.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "search_result"
)
data class SearchResult(
    @PrimaryKey
    val id: String,
    val isBookmarked: Boolean,
    val url: String,
    val thumbnailUrl: String,
)
