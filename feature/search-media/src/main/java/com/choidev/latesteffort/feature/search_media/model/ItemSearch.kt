package com.choidev.latesteffort.feature.search_media.model

import androidx.annotation.LayoutRes
import com.choidev.latesteffort.R
import java.util.Date

sealed class ItemSearch(
    @LayoutRes val layoutResId: Int
) {

    data class SearchResult(
        val thumbnailUrl: String,
        val dateTime: Date,
        var isBookmarked: Boolean = false
    ) : ItemSearch(R.layout.item_search_result)

    data class SearchPage(
        val searchPageIndex: Int
    ) : ItemSearch(R.layout.item_search_page_header)
}


