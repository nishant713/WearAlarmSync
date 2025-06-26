package com.neolabs.alarm4

import android.content.Intent
import android.provider.AlarmClock
import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

class SyncListenerService : WearableListenerService() {
    override fun onMessageReceived(event: MessageEvent) {
        when (event.path) {
            "/dismiss" -> {
                Log.d("SyncListenerService", "Received /dismiss message from watch")
                val dismissIntent = Intent("android.intent.action.DISMISS_ALARM")
                dismissIntent.setPackage("com.google.android.deskclock")
                dismissIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true)
                dismissIntent.putExtra(AlarmClock.EXTRA_ALARM_SEARCH_MODE, AlarmClock.ALARM_SEARCH_MODE_NEXT)
                dismissIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(dismissIntent)
            }
            "/snooze" -> {
                Log.d("SyncListenerService", "Received /snooze message from watch")
                val snoozeIntent = Intent("android.intent.action.SNOOZE_ALARM")
                snoozeIntent.setPackage("com.google.android.deskclock")
                snoozeIntent.putExtra("android.intent.extra.alarm.SKIP_UI", true)
                snoozeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(snoozeIntent)
            }
        }
    }
} 