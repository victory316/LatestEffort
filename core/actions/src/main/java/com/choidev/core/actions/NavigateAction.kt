package com.choidev.core.actions

sealed interface NavigateAction : com.choidev.core.actions.Action {
    data class NavGraphDestination(val destination: String) : NavigateAction
    data class StartActivity(val screenClass: Class<*>) : NavigateAction
}
