package com.example.kakaobankhomework.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kakaobankhomework.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var searchAdapter: SearchAdapter? = null

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

                        if (!searchViewModel.isPaging) {
                            searchViewModel.searchImageMore(lastPosition)
                            searchViewModel.searchVideoMore(lastPosition)
                        }
                    }
                }
            })
        }

        binding.searchInput.setEndIconOnClickListener {
            searchViewModel.searchImage()
            searchViewModel.searchVideo()
        }
    }

    private fun initObserves() {
        lifecycleScope.launch {
            searchViewModel.searchResultFlow.collect { state ->
                Log.d("TAG", "initObserves: $state")

                searchAdapter?.submitList(state.searchResults)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
