package com.neolabs.alarm4

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.service.notification.NotificationListenerService
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.wearable.Wearable
import android.content.pm.PackageManager
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.CapabilityClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Advertise the capability for the Wear app to detect
        Wearable.getCapabilityClient(this).addLocalCapability("companion_installed")
        setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
                MainScreen()
            }
        }
    }
}

fun isNotificationServiceEnabled(context: Context): Boolean {
    val cn = ComponentName(context, AlarmNotificationListenerService::class.java)
    val flat = Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
    return flat?.split(":")?.any { it == cn.flattenToString() } == true
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    var permissionGranted by remember { mutableStateOf(isNotificationServiceEnabled(context)) }
    var isWatchAppInstalled by remember { mutableStateOf<Boolean?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                val capabilityInfo = Tasks.await(
                    Wearable.getCapabilityClient(context)
                        .getCapability("wear_installed", CapabilityClient.FILTER_REACHABLE)
                )
                isWatchAppInstalled = capabilityInfo.nodes.isNotEmpty()
            } catch (e: Exception) {
                isWatchAppInstalled = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Wear Alarm Sync",
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "A simple app to restore the ability to snooze/dismiss phone alarms from WearOS based watches",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "The app requires access to read notifications to be able to relay alarm to the watch whenever the alarm goes off",
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(60.dp))
        val buttonColor = when (permissionGranted) {
            true -> Color(0xFF4CAF50)
            false -> Color(0xFFFFEB3B)
        }
        Button(
            onClick = {
                permissionGranted = isNotificationServiceEnabled(context)
                if (permissionGranted) {
                    Toast.makeText(context, "Permission already granted. You can now close the app.", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
                    context.startActivity(intent)
                }
            },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            modifier = Modifier
                .width(220.dp)
                .height(56.dp),
            contentPadding = PaddingValues(0.dp),
            content = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (permissionGranted) "PERMISSION GRANTED" else "GRANT PERMISSION",
                        fontSize = 17.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(60.dp))
        // Watch app status button
        val watchButtonColor = when (isWatchAppInstalled) {
            true -> Color(0xFF4CAF50)
            false -> Color(0xFFFFEB3B)
            null -> Color.Gray
        }
        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        val capabilityInfo = withContext(Dispatchers.IO) {
                            Tasks.await(
                                Wearable.getCapabilityClient(context)
                                    .getCapability("wear_installed", CapabilityClient.FILTER_REACHABLE)
                            )
                        }
                        isWatchAppInstalled = capabilityInfo.nodes.isNotEmpty()
                    } catch (e: Exception) {
                        isWatchAppInstalled = false
                    }
                    if (isWatchAppInstalled == true) {
                        Toast.makeText(context, "Watch App Is Installed", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Please Install Watch App", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = watchButtonColor),
            modifier = Modifier
                .width(220.dp)
                .height(56.dp),
            contentPadding = PaddingValues(0.dp),
            content = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when (isWatchAppInstalled) {
                            true -> "WATCH APP INSTALLED"
                            false -> "WATCH APP MISSING"
                            null -> "CHECKING WATCH..."
                        },
                        fontSize = 17.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(60.dp))
        if (permissionGranted && isWatchAppInstalled == true) {
            Button(
                onClick = {
                    (context as? MainActivity)?.finish()
                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)), // Red
                modifier = Modifier
                    .width(220.dp)
                    .height(56.dp),
                contentPadding = PaddingValues(0.dp),
                content = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "EXIT",
                            fontSize = 17.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            )
        } else {
            Spacer(modifier = Modifier.height(56.dp)) // Reserve space for the button
        }
    }
} 