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
import com.choidev.core.actions.NotificationImportance
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.random.Random

private const val LE_CHANNEL_ID = ""
private const val LE_NOTIFICATION_GROUP = ""
private const val NOTIFICATION_REQUEST_CODE = 0
private const val NOTIFICATION_SUMMARY_ID = 1
private const val TARGET_ACTIVITY_NAME = "com.example.latesteffort.MainActivity"
private const val KEY_TEXT_REPLY = "key_text_reply"

class NotifierImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : Notifier {

    private fun Context.createNotification(
        priority: Int = NotificationCompat.PRIORITY_DEFAULT,
        block: NotificationCompat.Builder.() -> Unit
    ): Notification {
        ensureNotificationChannelExists()
        return NotificationCompat.Builder(this, LE_CHANNEL_ID)
            .setPriority(priority)
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

            val priority = when (action.importance) {
                NotificationImportance.DEFAULT -> NotificationCompat.PRIORITY_DEFAULT
                NotificationImportance.LOW -> NotificationCompat.PRIORITY_LOW
                NotificationImportance.MIN -> NotificationCompat.PRIORITY_MIN
                NotificationImportance.HIGH -> NotificationCompat.PRIORITY_HIGH
                NotificationImportance.MAX -> NotificationCompat.PRIORITY_MAX
            }

            val singleNotification = createNotification(
                priority = priority
            ) {
                setSmallIcon(R.mipmap.ic_launcher_foreground)
                    .setContentTitle(action.title)
                    .setContentText(action.message)
                    .setContentIntent(normalPendingIntent())
                    .setGroup(LE_NOTIFICATION_GROUP)
                    .setAutoCancel(true)
            }

            val summaryNotification = createNotification(
                priority = priority
            ) {
                val title = getString(R.string.le_notification_channel_name)
                setContentTitle(title)
                    .setContentText(title)
                    .setSmallIcon(R.mipmap.ic_launcher_foreground)
                    .setStyle(newsNotificationStyle(title))
                    .setGroup(LE_NOTIFICATION_GROUP)
                    .setGroupSummary(true)
                    .setAutoCancel(true)
                    .build()
            }

            val notificationManager = NotificationManagerCompat.from(this)

            notificationManager.notify(Random.nextInt(), singleNotification)
            notificationManager.notify(NOTIFICATION_SUMMARY_ID, summaryNotification)
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

            val singleNotification = createNotification {
                setSmallIcon(R.mipmap.ic_launcher_foreground)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setContentTitle("미디어 재생")
                    .setContentText("음악")
                    .addAction(
                        R.drawable.ic_previous,
                        "Previous", normalPendingIntent()
                    )
                    .addAction(
                        R.drawable.ic_play,
                        "Pause", normalPendingIntent()
                    )
                    .addAction(
                        R.drawable.ic_next,
                        "Next", normalPendingIntent()
                    )
                    .setStyle(
                        androidx.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(0, 1, 2)
                    )
                    .setContentIntent(normalPendingIntent())
                    .setAutoCancel(true)
            }

            val notificationManager = NotificationManagerCompat.from(this)

            notificationManager.notify(Random.nextInt(), singleNotification)
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

            var message1 = NotificationCompat.MessagingStyle.Message(
                "Hey man",
                System.currentTimeMillis(),
                "Steve"
            )

            var message2 = NotificationCompat.MessagingStyle.Message(
                "What's up?",
                System.currentTimeMillis(),
                "Jeff"
            )

            var message3 = NotificationCompat.MessagingStyle.Message(
                "Nevermind.",
                System.currentTimeMillis(),
                "Steve"
            )

            val singleNotification = createNotification {
                setSmallIcon(R.mipmap.ic_launcher_foreground)
                    .setContentIntent(normalPendingIntent())
                    .setStyle(
                        NotificationCompat.MessagingStyle("ho")
                            .addMessage(message1)
                            .addMessage(message2)
                            .addMessage(message3)
                    )
                    .setAutoCancel(true)
            }

            val notificationManager = NotificationManagerCompat.from(this)

            notificationManager.notify(Random.nextInt(), singleNotification)
        }
    }

    private fun newsNotificationStyle(
        title: String,
    ): NotificationCompat.InboxStyle = NotificationCompat.InboxStyle()
        .setBigContentTitle(title)
        .setSummaryText(title)

    private fun Context.normalPendingIntent(): PendingIntent? = PendingIntent.getActivity(
        this,
        NOTIFICATION_REQUEST_CODE,
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
