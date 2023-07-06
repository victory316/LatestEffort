package com.example.kakaobankhomework.ui.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kakaobankhomework.databinding.ItemBookmarkedBinding
import com.example.kakaobankhomework.model.ItemBookmarked
import com.example.kakaobankhomework.model.SearchItem

class BookmarkAdapter(private val viewModel: BookmarkViewModel) :
    ListAdapter<ItemBookmarked, BookmarkAdapter.ViewHolder>(TaskDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemBookmarkedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: BookmarkViewModel, item: ItemBookmarked) {

            when (item) {
                is ItemBookmarked -> {
                    binding.item = item
                    binding.viewModel = viewModel
                }
            }

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

class TaskDiffCallback : DiffUtil.ItemCallback<ItemBookmarked>() {
    override fun areItemsTheSame(oldItem: ItemBookmarked, newItem: ItemBookmarked): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: ItemBookmarked, newItem: ItemBookmarked): Boolean {
        return oldItem == newItem
    }
}