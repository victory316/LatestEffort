package com.example.latesteffort

import LeTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.choidev.core.actions.Action
import com.choidev.core.actions.NavigateAction
import com.choidev.core.actions.NavigateAction.NavGraphDestination
import com.choidev.core.actions.NavigateAction.StartActivity
import com.choidev.core.actions.presenter.SimpleActionPresenter
import com.example.latesteffort.ext.startNewActivity
import com.example.latesteffort.navigation.LeNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUi()
    }

    private fun setupUi() {
        setContent {
            val navController = rememberNavController()

            val presenter = object : com.choidev.core.actions.presenter.SimpleActionPresenter() {
                override fun onClick(action: com.choidev.core.actions.Action) {
                    when (action) {
                        is com.choidev.core.actions.NavigateAction -> {
                            when (action) {
                                is NavGraphDestination -> {
                                    navController.navigate(action.destination)
                                }

                                is StartActivity -> {
                                    startNewActivity(action.screenClass)
                                }
                            }
                        }
                    }
                }
            }

            LeTheme {
                LeNavHost(
                    navController = navController,
                    presenter = presenter
                )
            }
        }
    }
}
