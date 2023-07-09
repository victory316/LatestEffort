package com.example.kakaobankhomework.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kakaobankhomework.RxBus
import com.example.kakaobankhomework.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var searchAdapter: SearchAdapter? = null

    private var eventBus: Disposable? = null

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@SearchFragment
            viewModel = searchViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        initSubscribes()
        initObserves()
    }

    private fun setupUi() {
        searchAdapter = SearchAdapter(searchViewModel)

        binding.searchRecyclerView.apply {
            adapter = searchAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    (layoutManager as? LinearLayoutManager)?.let {
                        val lastPosition = it.findLastVisibleItemPosition()

                        searchViewModel.searchImageMore(lastPosition)
                        searchViewModel.searchVideoMore(lastPosition)
                    }
                }
            })
        }
    }

    private fun initSubscribes() {
        eventBus = RxBus.onBookmarkRemoved
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { url ->
                searchViewModel.updateImageBookmark(url = url, bookmarked = false)
                searchViewModel.updateVideoBookmark(url = url, bookmarked = false)
            }
    }

    private fun initObserves() {
        lifecycleScope.launch {
            searchViewModel.searchResultFlow.collect { state ->
                searchAdapter?.submitList(state.searchResults)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        eventBus?.dispose()
    }
}
