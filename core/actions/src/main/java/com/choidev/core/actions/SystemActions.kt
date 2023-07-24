package com.choidev.core.actions

sealed interface SystemAction : Action {
    data class ShowToast(val message: String) : SystemAction
}
