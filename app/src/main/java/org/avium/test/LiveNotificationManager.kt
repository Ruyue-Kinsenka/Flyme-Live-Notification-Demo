package org.avium.test

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.*

class LiveNotificationManager(private val context: Context) {
    
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val channelId = "hello_world_live"
    
    init {
        createNotificationChannel()
    }
    
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            channelId,
            "Hello World Live Notifications",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "实况通知演示频道"
        }
        notificationManager.createNotificationChannel(channel)
    }
    
    fun showHelloWorldLiveNotification() {
        // 创建胶囊内容
        val capsuleRemoteViews = RemoteViews(context.packageName, R.layout.live_notification_capsule)
        capsuleRemoteViews.setTextViewText(R.id.capsule_content, "Hello World")
        
        // 创建胶囊配置
        val capsuleBundle = Bundle().apply {
            putInt("notification.live.capsuleStatus", 1)
            putInt("notification.live.capsuleType", 5)
            putParcelable("notification.live.capsuleIcon", 
                Icon.createWithResource(context, R.drawable.ic_notification))
            putInt("notification.live.capsuleBgColor", 
                context.resources.getColor(android.R.color.holo_blue_bright, null))
            putInt("notification.live.capsuleContentColor", 
                context.resources.getColor(android.R.color.white, null))
            putParcelable("notification.live.capsule.content.remote.view", capsuleRemoteViews)
        }
        
        // 创建实况通知Bundle
        val liveBundle = Bundle().apply {
            putBoolean("is_live", true)
            putInt("notification.live.operation", 0)
            putInt("notification.live.type",2)
            putBundle("notification.live.capsule", capsuleBundle)
        }
        
        // 创建主通知内容
        val contentRemoteViews = RemoteViews(context.packageName, R.layout.live_notification_hello_world)
        val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        contentRemoteViews.setTextViewText(R.id.tv_time, "发送时间: $currentTime")
        
        // 创建点击意图
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // 创建通知
        val notification = Notification.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
            .setContentTitle("Hello World 实况通知")
            .setContentText("这是一个实况通知演示")
            .setContentIntent(pendingIntent)
            .setShowWhen(true)
            .addExtras(liveBundle)
            .setAutoCancel(false)
            .setVisibility(Notification.VISIBILITY_PUBLIC)
            .build()
        
        // 设置自定义布局
        notification.contentView = contentRemoteViews
        
        // 发送通知
        notificationManager.notify(HELLO_WORLD_NOTIFICATION_ID, notification)
    }
    
    fun cancelHelloWorldNotification() {
        notificationManager.cancel(HELLO_WORLD_NOTIFICATION_ID)
    }
    
    companion object {
        private const val HELLO_WORLD_NOTIFICATION_ID = 1001
    }
}