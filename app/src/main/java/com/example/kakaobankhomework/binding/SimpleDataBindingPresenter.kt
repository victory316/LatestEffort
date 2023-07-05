package com.example.kakaobankhomework.binding

import android.view.View
import com.example.kakaobankhomework.action.Action

open class SimpleDataBindingPresenter : DataBindingPresenter {
    override fun onClick(view: View, item: Action) {}

    override fun onLongClick(view: View, item: Action): Boolean = false

    override fun onCheckedChanged(view: View, item: Action, checked: Boolean) {}
}