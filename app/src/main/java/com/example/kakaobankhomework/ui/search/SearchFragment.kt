package com.example.kakaobankhomework.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.kakaobankhomework.action.Action
import com.example.kakaobankhomework.binding.SimpleDataBindingPresenter
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
        val presenter = object : SimpleDataBindingPresenter() {
            override fun onClick(view: View, item: Action) {
                when (item) {
                    is Action.ItemSaveChanged -> {

                    }
                }
            }
        }

        searchAdapter = SearchAdapter(presenter)

        binding.searchRecyclerView.apply {
            adapter = searchAdapter
        }

        binding.searchInput.setEndIconOnClickListener {
            searchViewModel.searchImage()
            searchViewModel.searchVideo()
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
    }
}