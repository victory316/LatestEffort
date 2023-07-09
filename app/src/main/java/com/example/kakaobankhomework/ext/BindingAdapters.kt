package com.example.kakaobankhomework.ext

import android.widget.ImageView
import android.widget.TextView
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