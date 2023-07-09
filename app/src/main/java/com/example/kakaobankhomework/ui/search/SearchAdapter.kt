package com.example.kakaobankhomework.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kakaobankhomework.databinding.ItemSearchPageHeaderBinding
import com.example.kakaobankhomework.databinding.ItemSearchResultBinding
import com.example.kakaobankhomework.model.ItemOnSearch

class SearchAdapter(private val viewModel: SearchViewModel) :
    ListAdapter<ItemOnSearch, SearchAdapter.ViewHolder>(TaskDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, viewType)
    }

    override fun getItemViewType(position: Int) = getItem(position).layoutResId

    class ViewHolder private constructor(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: SearchViewModel, item: ItemOnSearch) {
            when (binding) {
                is ItemSearchResultBinding -> {
                    (item as? ItemOnSearch.SearchResult)?.let {
                        binding.item = item
                        binding.viewModel = viewModel
                    }
                }

                is ItemSearchPageHeaderBinding -> {
                    (item as? ItemOnSearch.SearchPage)?.let {
                        binding.item = item
                    }
                }
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup, viewType: Int): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    DataBindingUtil.inflate<ViewDataBinding>(
                        layoutInflater,
                        viewType,
                        parent,
                        false
                    )

                return ViewHolder(binding)
            }
        }
    }
}

class TaskDiffCallback : DiffUtil.ItemCallback<ItemOnSearch>() {
    override fun areItemsTheSame(oldItem: ItemOnSearch, newItem: ItemOnSearch): Boolean {
        return when {
            oldItem is ItemOnSearch.SearchResult && newItem is ItemOnSearch.SearchResult -> {
                oldItem.thumbnailUrl == newItem.thumbnailUrl
            }

            else -> oldItem.hashCode() == newItem.hashCode()
        }
    }

    override fun areContentsTheSame(oldItem: ItemOnSearch, newItem: ItemOnSearch): Boolean {
        return oldItem == newItem
    }
}