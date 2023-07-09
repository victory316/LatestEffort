package com.example.kakaobankhomework.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kakaobankhomework.databinding.FragmentBookmarkBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private var bookmarkAdapter: BookmarkAdapter? = null

    private val bookmarkViewModel: BookmarkViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@BookmarkFragment
            viewModel = bookmarkViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObserves()
        loadOnResumed()
    }

    private fun initViews() {
        bookmarkAdapter = BookmarkAdapter(bookmarkViewModel)

        binding.bookmarkRecyclerView.adapter = bookmarkAdapter
    }

    private fun initObserves() {
        lifecycleScope.launch {
            bookmarkViewModel.bookmakrs.collect { bookmarked ->
                bookmarked?.let { bookmarkAdapter?.submitList(it) }
            }
        }
    }

    private fun loadOnResumed() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                bookmarkViewModel.loadBookmarks()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
