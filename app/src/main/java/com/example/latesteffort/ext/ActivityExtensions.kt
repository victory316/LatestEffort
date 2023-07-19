package com.example.latesteffort.ext

import android.app.Activity
import android.content.Intent

fun Activity.startNewActivity(activity: Class<*>) {
    startActivity(
        Intent(this, activity)
    )
}
