package com.example.latesteffort.ext

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, imageUrl: String?) {
    Glide.with(imageView.context)
        .load(imageUrl)
        .centerCrop()
        .into(imageView)
}

@BindingAdapter("headerPageIndex")
fun setPageIndexString(textView: TextView, page: Int) {
    textView.apply {
        text = String.format("PAGE %d", page)
    }
}

@BindingAdapter("isVisible")
fun setVisible(view: View, value: Boolean?) {
    view.isVisible = value ?: false
}
