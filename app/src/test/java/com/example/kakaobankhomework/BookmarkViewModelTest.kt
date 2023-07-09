package com.example.kakaobankhomework

import com.example.kakaobankhomework.ui.bookmark.BookmarkViewModel
import com.example.testing.TestBookmarkUseCase
import org.junit.Before
import org.junit.Rule

class BookmarkViewModelTest {

    private lateinit var viewModel: BookmarkViewModel

    @Before
    fun setup() {
        viewModel = BookmarkViewModel(TestBookmarkUseCase())
    }
}