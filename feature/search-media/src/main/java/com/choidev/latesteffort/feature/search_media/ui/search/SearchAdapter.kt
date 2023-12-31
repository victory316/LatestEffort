package com.choidev.latesteffort.feature.search_media.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.choidev.latesteffort.feature.search_media.NewSearchViewModel
import com.choidev.latesteffort.feature.search_media.databinding.ItemSearchPageHeaderBinding
import com.choidev.latesteffort.feature.search_media.databinding.ItemSearchResultBinding
import com.choidev.latesteffort.feature.search_media.model.ItemSearch

class SearchAdapter(private val viewModel: NewSearchViewModel) :
    ListAdapter<ItemSearch, SearchAdapter.ViewHolder>(TaskDiffCallback()) {

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

        fun bind(viewModel: NewSearchViewModel, item: ItemSearch) {
            when (binding) {
                is ItemSearchResultBinding -> {
                    (item as? ItemSearch.SearchResult)?.let {
                        binding.item = item
                        binding.viewModel = viewModel
                    }
                }

                is ItemSearchPageHeaderBinding -> {
                    (item as? ItemSearch.SearchPage)?.let {
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

class TaskDiffCallback : DiffUtil.ItemCallback<ItemSearch>() {
    override fun areItemsTheSame(oldItem: ItemSearch, newItem: ItemSearch): Boolean {
        return when {
            oldItem is ItemSearch.SearchResult && newItem is ItemSearch.SearchResult -> {
                oldItem.thumbnailUrl == newItem.thumbnailUrl
            }

            else -> oldItem.hashCode() == newItem.hashCode()
        }
    }

    override fun areContentsTheSame(oldItem: ItemSearch, newItem: ItemSearch): Boolean {
        return when {
            oldItem is ItemSearch.SearchResult && newItem is ItemSearch.SearchResult -> {
                oldItem.isBookmarked == newItem.isBookmarked
            }

            else -> oldItem == newItem
        }
    }
}
