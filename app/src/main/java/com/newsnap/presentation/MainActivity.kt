package com.newsnap.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.newsnap.presentation.componants.NewsScreen
import com.newsnap.ui.theme.NewSnapTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewSnapTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    NewsScreen(modifier = Modifier.padding(paddingValues))
                }
            }
        }
    }
}



