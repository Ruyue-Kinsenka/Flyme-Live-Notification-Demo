package org.avium.test

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import org.avium.test.ui.theme.TestTheme

class MainActivity : ComponentActivity() {
    
    private lateinit var liveNotificationManager: LiveNotificationManager
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
           Log.d("Test","non permission")
        } else {
            Log.d("Test","non permission2")
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        liveNotificationManager = LiveNotificationManager(this)
        checkNotificationPermission()
        setContent {
            TestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LiveNotificationDemo(
                        liveNotificationManager = liveNotificationManager,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
    
    private fun checkNotificationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}

@Composable
fun LiveNotificationDemo(
    liveNotificationManager: LiveNotificationManager,
    modifier: Modifier = Modifier
) {
    var notificationSent by remember { mutableStateOf(false) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = "实况通知 Demo",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Text(
            text = "点击发送实况通知",
            style = MaterialTheme.typography.bodyMedium
        )
        
        Button(
            onClick = {
                liveNotificationManager.showHelloWorldLiveNotification()
                notificationSent = true
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("发送实况通知")
        }
        
        if (notificationSent) {
            Button(
                onClick = {
                    liveNotificationManager.cancelHelloWorldNotification()
                    notificationSent = false
                },
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("取消通知")
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestTheme {
        Greeting("Android")
    }
}