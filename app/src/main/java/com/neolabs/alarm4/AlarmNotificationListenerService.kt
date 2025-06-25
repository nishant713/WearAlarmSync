package com.neolabs.alarm4

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.Wearable

class AlarmNotificationListenerService : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        // Only fire if the notification body contains 'Swipe to dismiss' (case sensitive)
        if (sbn.packageName == "com.google.android.deskclock") {
            val extras = sbn.notification.extras
            val text = (extras.getCharSequence("android.text") ?: "").toString()
            val title = (extras.getCharSequence("android.title") ?: "").toString()
            val content = "$title $text"
            if (text.contains("Swipe to stop")) {
                Log.d("AlarmNotificationListener", "DeskClock notification with body containing 'Swipe to stop': $content")
                // Send /alarm message to Wear with content as payload
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val nodeClient = Wearable.getNodeClient(this@AlarmNotificationListenerService)
                        val nodes = Tasks.await(nodeClient.connectedNodes)
                        for (node in nodes) {
                            Wearable.getMessageClient(this@AlarmNotificationListenerService)
                                .sendMessage(node.id, "/alarm", content.toByteArray(Charsets.UTF_8))
                            Log.d("AlarmNotificationListener", "Sent /alarm to ${node.displayName} with payload: $content")
                        }
                    } catch (e: Exception) {
                        Log.e("AlarmNotificationListener", "Failed to send /alarm message", e)
                    }
                }
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        // Check if the removed notification was an alarm notification
        if (sbn.packageName == "com.google.android.deskclock") {
            val extras = sbn.notification.extras
            val text = (extras.getCharSequence("android.text") ?: "").toString()
            if (text.contains("Swipe to stop")) {
                Log.d("AlarmNotificationListener", "Alarm notification removed - sending close message to watch")
                // Send /close message to Wear to close the alarm activity
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val nodeClient = Wearable.getNodeClient(this@AlarmNotificationListenerService)
                        val nodes = Tasks.await(nodeClient.connectedNodes)
                        for (node in nodes) {
                            Wearable.getMessageClient(this@AlarmNotificationListenerService)
                                .sendMessage(node.id, "/close", null)
                            Log.d("AlarmNotificationListener", "Sent /close to ${node.displayName}")
                        }
                    } catch (e: Exception) {
                        Log.e("AlarmNotificationListener", "Failed to send /close message", e)
                    }
                }
            }
        }
    }
} 