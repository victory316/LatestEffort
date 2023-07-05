package com.example.kakaobankhomework.ui.search

import androidx.databinding.ViewDataBinding
import com.example.kakaobankhomework.binding.DataBindingAdapter
import com.example.kakaobankhomework.binding.DataBindingPresenter
import com.example.kakaobankhomework.binding.SimpleDataBindingViewHolder
import com.example.kakaobankhomework.binding.SimpleItemDiffCallback
import com.example.kakaobankhomework.model.SearchResultItem

class SearchAdapter(presenter: DataBindingPresenter? = null) :
    DataBindingAdapter<SearchResultItem>(presenter, SimpleItemDiffCallback()) {

    override fun getItemViewType(position: Int) = getItem(position).layoutResId
    override fun createDataBindingViewHolder(binding: ViewDataBinding) =
        SimpleDataBindingViewHolder<SearchResultItem>(binding)
}
