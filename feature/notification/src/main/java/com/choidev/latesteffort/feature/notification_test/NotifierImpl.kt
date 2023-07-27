package com.choidev.latesteffort.feature.notification_test

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.choidev.core.actions.NotificationAction
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.random.Random

private const val LE_CHANNEL_ID = ""
private const val LE_NOTIFICATION_GROUP = ""
private const val NEWS_NOTIFICATION_REQUEST_CODE = 0
private const val NEWS_NOTIFICATION_SUMMARY_ID = 1
private const val TARGET_ACTIVITY_NAME = "com.example.latesteffort.MainActivity"

class NotifierImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : Notifier {

    private fun Context.createNewsNotification(
        block: NotificationCompat.Builder.() -> Unit,
    ): Notification {
        ensureNotificationChannelExists()
        return NotificationCompat.Builder(this, LE_CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .apply(block)
            .build()
    }

    private fun Context.ensureNotificationChannelExists() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channel = NotificationChannel(
            LE_CHANNEL_ID,
            getString(R.string.le_notification_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            description = getString(R.string.le_notification_channel_description)
        }

        NotificationManagerCompat.from(this).createNotificationChannel(channel)
    }

    override fun buildNotification(action: NotificationAction) {
        when (action) {
            is NotificationAction.BasicNotification -> showBasicNotification(action)
            NotificationAction.MediaNotification -> showMediaNotification()
            is NotificationAction.MessageNotification -> showMessageNotification()
        }
    }

    private fun showBasicNotification(action: NotificationAction.BasicNotification) {
        with(context) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }

            val singleNotification = createNewsNotification {
                setSmallIcon(R.mipmap.ic_launcher_foreground)
                    .setContentTitle(action.title)
                    .setContentText(action.message)
                    .setContentIntent(newsPendingIntent())
                    .setGroup(LE_NOTIFICATION_GROUP)
                    .setAutoCancel(true)
            }

            val summaryNotification = createNewsNotification {
                val title = getString(R.string.le_notification_channel_name)
                setContentTitle(title)
                    .setContentText(title)
                    .setSmallIcon(
                        com.google.android.material.R.drawable.ic_arrow_back_black_24,
                    )
                    .setStyle(newsNotificationStyle(title))
                    .setGroup(LE_NOTIFICATION_GROUP)
                    .setGroupSummary(true)
                    .setAutoCancel(true)
                    .build()
            }

            val notificationManager = NotificationManagerCompat.from(this)

            notificationManager.notify(Random.nextInt(), singleNotification)
            notificationManager.notify(NEWS_NOTIFICATION_SUMMARY_ID, summaryNotification)
        }
    }

    private fun showMediaNotification() {
        with(context) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }

            val singleNotification = createNewsNotification {
                setSmallIcon(R.mipmap.ic_launcher_foreground)
                    .setContentTitle("")
                    .setContentText("")
                    .setContentIntent(newsPendingIntent())
                    .setGroup(LE_NOTIFICATION_GROUP)
                    .setAutoCancel(true)
            }

            val summaryNotification = createNewsNotification {
                val title = getString(R.string.le_notification_channel_name)
                setContentTitle(title)
                    .setContentText(title)
                    .setSmallIcon(
                        com.google.android.material.R.drawable.ic_arrow_back_black_24,
                    )
                    .setGroup(LE_NOTIFICATION_GROUP)
                    .setGroupSummary(true)
                    .setAutoCancel(true)
                    .build()
            }

            val notificationManager = NotificationManagerCompat.from(this)

            notificationManager.notify(Random.nextInt(), singleNotification)
            notificationManager.notify(NEWS_NOTIFICATION_SUMMARY_ID, summaryNotification)
        }
    }

    private fun showMessageNotification() {
        with(context) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }

            val singleNotification = createNewsNotification {
                setSmallIcon(R.mipmap.ic_launcher_foreground)
                    .setContentTitle("")
                    .setContentText("")
                    .setContentIntent(newsPendingIntent())
                    .setGroup(LE_NOTIFICATION_GROUP)
                    .setAutoCancel(true)
            }

            val summaryNotification = createNewsNotification {
                val title = getString(R.string.le_notification_channel_name)
                setContentTitle(title)
                    .setContentText(title)
                    .setSmallIcon(
                        com.google.android.material.R.drawable.ic_arrow_back_black_24,
                    )
                    .setStyle(newsNotificationStyle(title))
                    .setGroup(LE_NOTIFICATION_GROUP)
                    .setGroupSummary(true)
                    .setAutoCancel(true)
                    .build()
            }

            val notificationManager = NotificationManagerCompat.from(this)

            notificationManager.notify(Random.nextInt(), singleNotification)
            notificationManager.notify(NEWS_NOTIFICATION_SUMMARY_ID, summaryNotification)
        }
    }

    private fun newsNotificationStyle(
        title: String,
    ): NotificationCompat.InboxStyle = NotificationCompat.InboxStyle()
        .setBigContentTitle(title)
        .setSummaryText(title)

    private fun Context.newsPendingIntent(): PendingIntent? = PendingIntent.getActivity(
        this,
        NEWS_NOTIFICATION_REQUEST_CODE,
        Intent().apply {
            action = Intent.ACTION_VIEW
            data = "www.naver.com".toUri()
            component = ComponentName(
                packageName,
                TARGET_ACTIVITY_NAME,
            )
        },
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )
}
