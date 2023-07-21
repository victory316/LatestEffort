package com.example.latesteffort.action

sealed interface NavigateAction : Action {
    data class NavGraphDestination(val destination: String) : NavigateAction
    data class StartActivity(val screenClass: Class<*>) : NavigateAction
}
