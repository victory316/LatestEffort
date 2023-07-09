package com.example.kakaobankhomework

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.model.result.Result
import com.example.domain.model.result.ServiceError
import com.example.kakaobankhomework.ui.search.SearchViewModel
import com.example.testing.TestBookmarkUseCase
import com.example.testing.TestSearchUseCase
import com.jraska.livedata.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
        viewModel = SearchViewModel(
            TestBookmarkUseCase().apply {
                setMockData(
                    listOf("test1", "test2", "test3")
                )
            },
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
        viewModel = SearchViewModel(
            TestBookmarkUseCase().apply {
                setMockData(
                    listOf("test1", "test2", "test3")
                )
            },
            TestSearchUseCase().apply {
                simulateExceptionOnVideo(Exception())
            }
        )

        viewModel.queryText.value = "a"

        viewModel.errorOccurred.test().awaitValue()

        viewModel.errorOccurred.test().assertValue(Result.Failure(ServiceError.SearchFail))
    }
}