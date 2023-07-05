package com.example.kakaobankhomework.binding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class DataBindingAdapter<T>(
    internal val presenter: DataBindingPresenter?,
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, DataBindingViewHolder<T>>(diffCallback), CoroutineScope by MainScope() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return createDataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<T>, position: Int) {
        holder.bind(getItem(position), presenter)
    }

    abstract fun createDataBindingViewHolder(binding: ViewDataBinding): DataBindingViewHolder<T>

    fun itemOf(position: Int): T = getItem(position)

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)

        cancel()
    }
}