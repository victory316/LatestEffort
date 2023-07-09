package com.example.kakaobankhomework

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kakaobankhomework.model.ItemBookmark
import com.example.kakaobankhomework.ui.bookmark.BookmarkViewModel
import com.example.testing.TestBookmarkUseCase
import com.jraska.livedata.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BookmarkViewModelTest {

    private lateinit var viewModel: BookmarkViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = BookmarkViewModel(
            TestBookmarkUseCase().apply {
                setMockData(
                    listOf("test1", "test2", "test3")
                )
            }
        )

        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `onBookmarkClick removes item from ItemBookmark`() {
        val bookmarkItem = ItemBookmark("test1")

        viewModel.onBookmarkClick(bookmarkItem)

        viewModel.loadBookmarks()

        assert(viewModel.bookmakrs.value?.contains(bookmarkItem) != true)
    }

    @Test
    fun `remove all bookmark set noBookmarks true`() {
        val bookmarkItems = listOf(
            ItemBookmark("test1"),
            ItemBookmark("test2"),
            ItemBookmark("test3"),
        )

        bookmarkItems.forEach {
            viewModel.onBookmarkClick(it)
        }

        viewModel.loadBookmarks()

        viewModel.noBookmarks.test().assertValue(true)
    }
}