package com.neolabs.alarm4

import android.content.Intent
import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

class SyncListenerService : WearableListenerService() {
    override fun onMessageReceived(event: MessageEvent) {
        when (event.path) {
            "/alarm" -> {
                Log.d("SyncListenerService", "Received /alarm message - launching alarm screen")
                val intent = Intent(this, SyncedActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                intent.putExtra("isAlarm", true)
                startActivity(intent)
            }
            "/close" -> {
                Log.d("SyncListenerService", "Received /close message - closing alarm activity")
                // Send broadcast to close the alarm activity
                val closeIntent = Intent("com.neolabs.alarm4.CLOSE_ALARM")
                sendBroadcast(closeIntent)
            }
        }
    }
} 