package com.example.kakaobankhomework.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.kakaobankhomework.R
import com.example.kakaobankhomework.binding.DataBindingAdapter
import com.example.kakaobankhomework.binding.DataBindingPresenter
import com.example.kakaobankhomework.binding.DataBindingViewHolder
import com.example.kakaobankhomework.binding.SimpleItemDiffCallback
import com.example.kakaobankhomework.model.SearchResult

class SearchAdapter(presenter: DataBindingPresenter? = null) :
    DataBindingAdapter<SearchResult>(presenter, SimpleItemDiffCallback()) {
        override fun createDataBindingViewHolder(binding: ViewDataBinding): DataBindingViewHolder<SearchResult> {
                TODO("Not yet implemented")
        }

}
