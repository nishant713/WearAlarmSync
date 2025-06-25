package com.neolabs.alarm4

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Advertise the capability for the phone app to detect
        Wearable.getCapabilityClient(this).addLocalCapability("wear_installed")
        setContent {
            WearAlarmSyncScreen()
        }
    }
}

@Composable
fun WearAlarmSyncScreen() {
    val context = LocalContext.current
    var isCompanionInstalled by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                val capabilityInfo = Tasks.await(
                    Wearable.getCapabilityClient(context)
                        .getCapability("companion_installed", CapabilityClient.FILTER_REACHABLE)
                )
                isCompanionInstalled = capabilityInfo.nodes.isNotEmpty()
            } catch (e: Exception) {
                isCompanionInstalled = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Wear Alarm Sync",
                fontSize = 24.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
            when (isCompanionInstalled) {
                null -> {
                    Text(
                        text = "Checking for companion app...",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                false -> {
                    Text(
                        text = "INSTALL COMPANION APP FIRST",
                        fontSize = 16.sp,
                        color = Color(0xFFFFEB3B), // Yellow
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                true -> {
                    Text(
                        text = "Wear Alarm Sync\nis configured properly",
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    androidx.compose.material3.Button(
                        onClick = { (context as? MainActivity)?.finish() },
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Text(
                            text = "✅",
                            fontSize = 32.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
} 