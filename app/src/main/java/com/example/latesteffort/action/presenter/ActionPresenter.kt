package com.example.latesteffort.action.presenter

import com.example.latesteffort.action.Action

interface ActionPresenter {
    fun onClick(action: Action)
    fun onDoubleClick(action: Action)
    fun onLongClick(action: Action)
}
