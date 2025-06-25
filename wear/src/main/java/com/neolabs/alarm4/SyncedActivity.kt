package com.neolabs.alarm4

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.PowerManager
import android.os.Vibrator
import android.os.VibrationEffect
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.Arrangement
import com.google.android.gms.wearable.Wearable
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.ui.text.font.FontWeight
import androidx.wear.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Snooze
import androidx.compose.material.icons.filled.AlarmOff
import androidx.wear.compose.material.Icon

class SyncedActivity : ComponentActivity() {
    private var ringtone: Ringtone? = null
    private var vibrator: Vibrator? = null
    private var wakeLock: PowerManager.WakeLock? = null
    private lateinit var closeReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isAlarm = intent.getBooleanExtra("isAlarm", false)

        // Register broadcast receiver for close message
        closeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == "com.neolabs.alarm4.CLOSE_ALARM") {
                    android.util.Log.d("SyncedActivity", "Received close broadcast - finishing activity")
                    stopAlarm()
                    finish()
                }
            }
        }
        registerReceiver(closeReceiver, IntentFilter("com.neolabs.alarm4.CLOSE_ALARM"))

        // Wake up the screen
        setShowWhenLocked(true)
        setTurnScreenOn(true)
        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = pm.newWakeLock(
            PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
            "alarm4:AlarmWakeLock"
        )
        wakeLock?.acquire(60 * 1000L) // 1 minute max

        setContent {
            if (isAlarm) {
                AlarmScreen(
                    onStop = {
                        android.util.Log.d("SyncedActivity", "Stopping alarm and finishing activity")
                        stopAlarm()
                        finish()
                    },
                    startAlarm = {
                        startAlarm()
                    }
                )
            }
        }
    }

    private fun startAlarm() {
        // Play default alarm sound
        val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        ringtone = RingtoneManager.getRingtone(applicationContext, alarmUri)
        ringtone?.play()
        // Vibrate
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val effect = VibrationEffect.createWaveform(longArrayOf(0, 500, 500, 500), 0)
        vibrator?.vibrate(effect)
    }

    private fun stopAlarm() {
        ringtone?.stop()
        vibrator?.cancel()
        if (wakeLock?.isHeld == true) {
            wakeLock?.release()
        }
        wakeLock = null
    }

    override fun onDestroy() {
        android.util.Log.d("SyncedActivity", "onDestroy called")
        try {
            unregisterReceiver(closeReceiver)
        } catch (e: Exception) {
            // Receiver might not be registered
        }
        stopAlarm()
        super.onDestroy()
    }
}

@Composable
fun AlarmScreen(onStop: () -> Unit, startAlarm: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    var started by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (!started) {
            startAlarm()
            started = true
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ALARM!",
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(48.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                // Snooze Button (Left)
                Button(
                    onClick = {
                        android.util.Log.d("SyncedActivity", "Snooze button pressed")
                        // Send /snooze message to phone in background
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val nodeClient = Wearable.getNodeClient(context)
                                val nodes = Tasks.await(nodeClient.connectedNodes)
                                for (node in nodes) {
                                    Wearable.getMessageClient(context)
                                        .sendMessage(node.id, "/snooze", null)
                                }
                            } catch (_: Exception) {}
                        }
                        // Stop alarm and close activity immediately
                        onStop()
                    },
                    shape = CircleShape,
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Snooze,
                        contentDescription = "Snooze",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
                // Stop Button (Right)
                Button(
                    onClick = {
                        android.util.Log.d("SyncedActivity", "Dismiss button pressed")
                        // Send /dismiss message to phone in background
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val nodeClient = Wearable.getNodeClient(context)
                                val nodes = Tasks.await(nodeClient.connectedNodes)
                                for (node in nodes) {
                                    Wearable.getMessageClient(context)
                                        .sendMessage(node.id, "/dismiss", null)
                                }
                            } catch (_: Exception) {}
                        }
                        // Stop alarm and close activity immediately
                        onStop()
                    },
                    shape = CircleShape,
                    modifier = Modifier.size(64.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD21F3C)) // Red
                ) {
                    Icon(
                        imageVector = Icons.Filled.AlarmOff,
                        contentDescription = "Stop",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
} 