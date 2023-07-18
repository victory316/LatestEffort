package com.example.latesteffort

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.BookmarkUseCase
import com.example.domain.model.result.Result
import com.example.domain.model.result.ServiceError
import com.example.latesteffort.model.ItemSearch
import com.example.latesteffort.ui.search.SearchViewModel
import com.example.testing.TestSearchUseCase
import com.jraska.livedata.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.anyString
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `set error on searchImageState has exception`() {
        val bookmarkMock = mock<BookmarkUseCase>()

        viewModel = SearchViewModel(
            bookmarkMock,
            TestSearchUseCase().apply {
                simulateExceptionOnImage(Exception())
            }
        )

        viewModel.queryText.value = "a"

        viewModel.errorOccurred.test().awaitValue()

        viewModel.errorOccurred.test().assertValue(Result.Failure(ServiceError.SearchFail))
    }

    @Test
    fun `set error on searchVideoState has exception`() {
        val bookmarkMock = mock<BookmarkUseCase>()

        viewModel = SearchViewModel(
            bookmarkMock,
            TestSearchUseCase().apply {
                simulateExceptionOnVideo(Exception())
            }
        )

        viewModel.queryText.value = "a"

        viewModel.errorOccurred.test().awaitValue()
        viewModel.errorOccurred.test().assertValue(Result.Failure(ServiceError.SearchFail))
    }

    @Test
    fun `on bookmark true click invoke removeBookmark`() {
        val bookmarkMock = mock<BookmarkUseCase>()
        val resultMock = mock<ItemSearch.SearchResult> {
            on { isBookmarked } doReturn true
            on { thumbnailUrl } doReturn "thumbnailUrl"
        }

        viewModel = SearchViewModel(
            bookmarkMock,
            TestSearchUseCase().apply {
                simulateExceptionOnImage(Exception())
            }
        )

        viewModel.onBookmarkClick(resultMock)

        verify(bookmarkMock, times(1)).removeBookmark(anyString())
    }

    @Test
    fun `on bookmark false click invoke addBookmark`() {
        val bookmarkMock = mock<BookmarkUseCase>()
        val resultMock = mock<ItemSearch.SearchResult> {
            on { isBookmarked } doReturn false
            on { thumbnailUrl } doReturn "thumbnailUrl"
        }

        viewModel = SearchViewModel(
            bookmarkMock,
            TestSearchUseCase().apply {
                simulateExceptionOnImage(Exception())
            }
        )

        viewModel.onBookmarkClick(resultMock)

        verify(bookmarkMock, times(1)).addBookmark(anyString())
    }
}
