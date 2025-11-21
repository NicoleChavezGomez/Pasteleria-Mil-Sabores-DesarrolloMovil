package com.example.milsaborestest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.SystemBarStyle
import androidx.navigation.compose.rememberNavController
import com.example.milsaborestest.presentation.ui.MainContent
import com.example.milsaborestest.ui.theme.MilSaboresTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Configurar edge-to-edge con color rosa fuerte para status bar y blanco para navigation bar
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = 0xFFD63384.toInt(),
                darkScrim = 0xFFD63384.toInt()
            ),
            navigationBarStyle = SystemBarStyle.light(scrim = 0xFFFFFFFF.toInt(), darkScrim = 0xFFFFFFFF.toInt())
        )
        
        setContent {
            MilSaboresTestTheme {
                val navController = rememberNavController()
                MainContent(navController = navController)
            }
        }
    }
}
