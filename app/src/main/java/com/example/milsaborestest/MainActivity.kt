package com.example.milsaborestest

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.SystemBarStyle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.navigation.compose.rememberNavController
import com.example.milsaborestest.presentation.ui.MainContent
import com.example.milsaborestest.presentation.viewmodel.CartViewModel
import com.example.milsaborestest.ui.theme.MilSaboresTestTheme
import com.example.milsaborestest.util.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    
    private val activityScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var cartViewModel: CartViewModel? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Crear canal de notificaciones
        NotificationHelper.createNotificationChannel(this)
        
        // Configurar edge-to-edge con color rosa fuerte para status bar y blanco para navigation bar
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = 0xFFD63384.toInt(),
                darkScrim = 0xFFD63384.toInt()
            ),
            navigationBarStyle = SystemBarStyle.light(scrim = 0xFFFFFFFF.toInt(), darkScrim = 0xFFFFFFFF.toInt())
        )
        
        // Inicializar CartViewModel para poder accederlo en onPause
        val factory = AndroidViewModelFactory.getInstance(application)
        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]
        
        // Manejar navegación desde notificación
        handleNotificationNavigation(intent)
        
        setContent {
            MilSaboresTestTheme {
                val navController = rememberNavController()
                MainContent(navController = navController)
            }
        }
    }
    
    override fun onPause() {
        super.onPause()
        
        // Verificar si hay items en el carrito cuando la app pierde foco
        cartViewModel?.let { viewModel ->
            activityScope.launch {
                val totalItems = viewModel.totalItems.value
                if (totalItems > 0) {
                    // Mostrar notificación inmediatamente si hay items en el carrito
                    NotificationHelper.showCartReminderNotification(this@MainActivity, totalItems)
                }
            }
        }
    }
    
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { handleNotificationNavigation(it) }
    }
    
    /**
     * Maneja la navegación cuando la app se abre desde una notificación
     */
    private fun handleNotificationNavigation(intent: Intent?) {
        val navigateTo = intent?.getStringExtra("navigate_to")
        if (navigateTo == "cart") {
            // La navegación al carrito se manejará en MainContent cuando detecte el intent
            // Por ahora, solo limpiamos el extra para evitar navegación duplicada
            intent.removeExtra("navigate_to")
        }
    }
}
