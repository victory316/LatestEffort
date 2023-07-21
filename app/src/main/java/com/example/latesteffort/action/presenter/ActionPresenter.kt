package com.example.latesteffort.ui.action.presenter

import com.example.latesteffort.ui.action.Action

interface ActionPresenter {
    fun onClick(action: Action)
    fun onDoubleClick(action: Action)
    fun onLongClick(action: Action)
}
