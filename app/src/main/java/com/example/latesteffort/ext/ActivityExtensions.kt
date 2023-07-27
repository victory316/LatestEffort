package com.example.latesteffort.ext

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.navigation.NavHostController
import com.choidev.core.actions.NavigateAction
import com.choidev.core.actions.SystemAction
import com.choidev.core.actions.VibrateAction
import com.choidev.core.actions.mapToId
import com.choidev.latesteffort.core.util.vibration.VibrationManager

fun Activity.startNewActivity(activity: Class<*>) {
    startActivity(
        Intent(this, activity)
    )
}

fun Activity.handleNavigateAction(
    navController: NavHostController,
    action: NavigateAction
) {
    when (action) {
        is NavigateAction.NavGraphDestination -> {
            navController.navigate(action.destination)
        }

        is NavigateAction.StartActivity -> {
            startNewActivity(action.screenClass)
        }
    }
}

fun Activity.handleSystemAction(action: SystemAction) {
    when (action) {
        is SystemAction.ShowToast -> {
            Toast.makeText(this, action.message, Toast.LENGTH_SHORT).show()
        }
    }
}

fun Activity.handleVibrateAction(
    action: VibrateAction,
    vibrationManager: VibrationManager
) {
    when (action) {
        is VibrateAction.Vibrate -> {
            if (action.activate) {
                vibrationManager.vibrate(
                    duration = action.duration,
                    amplitude = action.amplitude
                )
            }
        }

        is VibrateAction.VibrateEffect -> {
            vibrationManager.vibrateEffect(action.effect.mapToId())
        }

        is VibrateAction.VibratePattern -> {
            vibrationManager.vibrateWithPattern(
                timing = action.patterns.map { it.first.toLong() }.toLongArray(),
                amplitudes = action.patterns.map { it.second }.toIntArray(),
                repeat = if (action.repeat) 0 else -1
            )
        }

        VibrateAction.StopVibration -> {
            vibrationManager.stopVibration()
        }
    }
}
