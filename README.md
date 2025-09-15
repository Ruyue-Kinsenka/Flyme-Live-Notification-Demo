## 核心代码说明（Ai润色）

### LiveNotificationManager.kt

主要负责实况通知的创建和管理：

```kotlin
// 创建胶囊内容
val capsuleRemoteViews = RemoteViews(context.packageName, R.layout.live_notification_capsule)
capsuleRemoteViews.setTextViewText(R.id.capsule_content, "Hello World")

// 胶囊配置
val capsuleBundle = Bundle().apply {
    putInt("notification.live.capsuleStatus", 1)
    putInt("notification.live.capsuleType", 1)
    putParcelable("notification.live.capsuleIcon", Icon.createWithResource(context, R.drawable.ic_notification))
    putInt("notification.live.capsuleBgColor", context.resources.getColor(android.R.color.holo_blue_bright, null))
    putInt("notification.live.capsuleContentColor", context.resources.getColor(android.R.color.white, null))
    putParcelable("notification.live.capsule.content.remote.view", capsuleRemoteViews)
    putString("notification.live.capsuleContent", "test");
}
```

### 关键配置参数

| 参数 | 值 | 说明 |
|------|-----|------|
| `notification.live.capsuleStatus` | idk, need int | 启用胶囊状态 |
| `notification.live.capsuleType` | Idk, need int | 胶囊类型（标准） |
| `notification.live.operation` | Idk, need int | 操作类型（显示） |
| `notification.live.type` | idk, need int | 实况通知类型 |

## 自定义开发

### 修改胶囊内容

编辑 `live_notification_capsule.xml`：
```xml
<TextView
    android:id="@+id/capsule_content"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="So meizu, ** u(linus 中指)"
    android:textColor="#FFFFFF"
    android:background="@android:color/holo_green_dark"
    android:padding="6dp" />
```

### 修改主通知布局即胶囊点击后的卡片

 `live_notification_hello_world.xml`：
### 添加交互功能

在 `LiveNotificationManager.kt` 中添加 PendingIntent：
```kotlin
val intent = Intent(context, YourActivity::class.java)
val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
contentRemoteViews.setOnClickPendingIntent(R.id.your_button, pendingIntent)
```
