package com.choidev.latesteffort.feature.search_media.ui.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.choidev.latesteffort.feature.search_media.NewSearchViewModel
import com.choidev.latesteffort.feature.search_media.databinding.ItemBookmarkedBinding
import com.choidev.latesteffort.feature.search_media.model.ItemBookmark

class BookmarkAdapter(private val viewModel: NewSearchViewModel) :
    ListAdapter<ItemBookmark, BookmarkAdapter.ViewHolder>(TaskDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemBookmarkedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: NewSearchViewModel, item: ItemBookmark) {
            binding.item = item
            binding.viewModel = viewModel

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemBookmarkedBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class TaskDiffCallback : DiffUtil.ItemCallback<ItemBookmark>() {
    override fun areItemsTheSame(oldItem: ItemBookmark, newItem: ItemBookmark): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: ItemBookmark, newItem: ItemBookmark): Boolean {
        return oldItem == newItem
    }
}
