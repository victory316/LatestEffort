package com.supergene.loki.feature.motion

import android.util.Log
import androidx.lifecycle.ViewModel
import com.choidev.latesteffort.core.util.motion.MotionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MotionViewModel @Inject constructor(
    motionManager: MotionManager
) : ViewModel() {

    init {
        motionManager.observeAccelerometer {
            Log.d("LOGGING", "it works? $it")
        }
    }
}
