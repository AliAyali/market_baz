package com.aliayali.market_baz.presentation.screens.admin.components

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.aliayali.market_baz.R

fun Context.showAdminNotification(message: String) {
    val channelId = "orders_channel"
    val notificationManager =
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val channel = NotificationChannel(
        channelId,
        "Orders Notifications",
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        description = "نوتیفیکیشن سفارش جدید"
        enableLights(true)
        enableVibration(true)
    }
    notificationManager.createNotificationChannel(channel)

    val customView = RemoteViews(packageName, R.layout.notification_rtl).apply {
        setTextViewText(R.id.noti_title, "سفارش جدید")
        setTextViewText(R.id.noti_message, message)
    }

    val notification = NotificationCompat.Builder(this, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setCustomContentView(customView)
        .setStyle(NotificationCompat.DecoratedCustomViewStyle())
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        .build()

    notificationManager.notify(System.currentTimeMillis().toInt(), notification)


    notificationManager.notify(System.currentTimeMillis().toInt(), notification)
}

