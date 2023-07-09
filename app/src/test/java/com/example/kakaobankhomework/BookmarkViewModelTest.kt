package com.example.kakaobankhomework

import com.example.kakaobankhomework.model.ItemBookmark
import com.example.kakaobankhomework.ui.bookmark.BookmarkViewModel
import com.example.testing.TestBookmarkUseCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BookmarkViewModelTest {

    private lateinit var viewModel: BookmarkViewModel

    @Before
    fun setup() {
        viewModel = BookmarkViewModel(
            TestBookmarkUseCase().apply {
                setMockData(
                    listOf("test1", "test2", "test3")
                )
            }
        )
    }

    @Test
    fun `onBookmarkClick removes item from ItemBookmark`() {
        val bookmarkItem = ItemBookmark("test1")

        viewModel.onBookmarkClick(bookmarkItem)

        viewModel.loadBookmarks()

        assert(viewModel.bookmakrs.value?.contains(bookmarkItem) != true)
    }
}