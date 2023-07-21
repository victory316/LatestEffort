package com.choidev.core.actions.presenter

import com.choidev.core.actions.Action

interface ActionPresenter {
    fun onClick(action: com.choidev.core.actions.Action)
    fun onDoubleClick(action: com.choidev.core.actions.Action)
    fun onLongClick(action: com.choidev.core.actions.Action)
}
