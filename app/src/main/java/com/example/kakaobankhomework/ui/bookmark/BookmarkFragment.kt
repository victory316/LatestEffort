package com.example.kakaobankhomework.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.kakaobankhomework.databinding.FragmentBookmarkBinding
import com.example.kakaobankhomework.ui.search.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint

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
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false).apply {
            viewModel = bookmarkViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObserves()
    }

    private fun initViews() {
        bookmarkAdapter = BookmarkAdapter(bookmarkViewModel)
    }

    private fun initObserves() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
