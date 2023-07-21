package com.example.latesteffort

import LeTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.choidev.core.actions.Action
import com.choidev.core.actions.NavigateAction
import com.choidev.core.actions.NavigateAction.NavGraphDestination
import com.choidev.core.actions.NavigateAction.StartActivity
import com.choidev.core.actions.VibrationAction
import com.choidev.core.actions.presenter.SimpleActionPresenter
import com.choidev.latesteffort.core.util.vibration.VibrationManager
import com.example.latesteffort.ext.startNewActivity
import com.example.latesteffort.navigation.LeNavHost
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var vibrationManager: VibrationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUi()
    }

    private fun setupUi() {
        setContent {
            val navController = rememberNavController()

            val presenter = object : SimpleActionPresenter() {
                override fun onClick(action: Action) {
                    when (action) {
                        is NavigateAction -> handleNavigateAction(
                            navController = navController,
                            action = action
                        )

                        is VibrationAction -> handleVibrationAction(action)
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

    private fun handleNavigateAction(
        navController: NavHostController,
        action: NavigateAction
    ) {
        when (action) {
            is NavGraphDestination -> {
                navController.navigate(action.destination)
            }

            is StartActivity -> {
                startNewActivity(action.screenClass)
            }
        }
    }

    private fun handleVibrationAction(action: VibrationAction) {
    }
}
