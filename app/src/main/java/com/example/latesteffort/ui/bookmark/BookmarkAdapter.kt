package com.example.latesteffort.ui.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kakaobankhomework.databinding.ItemBookmarkedBinding
import com.example.latesteffort.model.ItemBookmark

class BookmarkAdapter(private val viewModel: BookmarkViewModel) :
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

        fun bind(viewModel: BookmarkViewModel, item: ItemBookmark) {
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
