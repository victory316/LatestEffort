package com.choidev.core.actions.presenter

import com.choidev.core.actions.Action

interface ActionPresenter {
    fun onClick(action: Action)
    fun onDoubleClick(action: Action)
    fun onLongClick(action: Action)
}
