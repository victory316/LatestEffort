package com.example.latesteffort

import LeTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.latesteffort.action.Action
import com.example.latesteffort.action.NavigateAction
import com.example.latesteffort.action.NavigateAction.NavGraphDestination
import com.example.latesteffort.action.NavigateAction.StartActivity
import com.example.latesteffort.action.presenter.SimpleActionPresenter
import com.example.latesteffort.ext.startNewActivity
import com.example.latesteffort.ui.CatalogScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUi()
    }

    private fun setupUi() {
        val presenter = object : SimpleActionPresenter() {
            override fun onClick(action: Action) {
                when (action) {
                    is NavigateAction -> {
                        when (action) {
                            is NavGraphDestination -> {
                                // TODO nav graph move action
                            }

                            is StartActivity -> {
                                startNewActivity(action.screenClass)
                            }
                        }
                    }
                }
            }
        }

        setContent {
            LeTheme {
                CatalogScreen(presenter)
            }
        }
    }
}
