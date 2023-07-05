package com.example.kakaobankhomework.binding

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.kakaobankhomework.databinding.ItemSearchResultBinding

abstract class DataBindingViewHolder<T>(internal val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T, presenter: DataBindingPresenter? = null) {
//        presenter?.let {
//            binding.setVariable(BR.presenter, it)
//        }
        bindItem(item)
        binding.executePendingBindings()
    }

    abstract fun bindItem(item: T)
}