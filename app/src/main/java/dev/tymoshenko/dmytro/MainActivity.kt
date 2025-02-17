package dev.tymoshenko.dmytro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dev.tymoshenko.dmytro.ui.screens.messages.Messages
import dev.tymoshenko.dmytro.ui.theme.DmytroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DmytroTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { pv ->
                    Messages()
                }
            }
        }
    }
}