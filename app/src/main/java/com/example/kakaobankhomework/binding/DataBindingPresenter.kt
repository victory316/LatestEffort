package com.example.kakaobankhomework.binding

import android.view.View
import com.example.kakaobankhomework.action.Action

interface DataBindingPresenter {

    fun onClick(view: View, item: Action)
    fun onLongClick(view: View, item: Action): Boolean
    fun onCheckedChanged(view: View, item: Action, checked: Boolean)
}