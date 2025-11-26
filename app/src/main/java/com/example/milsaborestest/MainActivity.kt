package com.example.milsaborestest

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.SystemBarStyle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.milsaborestest.presentation.ui.MainContent
import com.example.milsaborestest.presentation.viewmodel.CartViewModel
import com.example.milsaborestest.ui.theme.MilSaboresTestTheme
import com.example.milsaborestest.util.Constants
import com.example.milsaborestest.util.NotificationHelper
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    
    private var cartViewModel: CartViewModel? = null
    private var shouldNavigateToCart = false // Flag para navegar al carrito desde notificación
    
    // Launcher para solicitar permisos de notificaciones (Android 13+)
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d(Constants.TAG, "Permiso de notificaciones concedido")
        } else {
            Log.d(Constants.TAG, "Permiso de notificaciones denegado")
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Crear canal de notificaciones
        NotificationHelper.createNotificationChannel(this)
        
        // Solicitar permiso de notificaciones si es Android 13+ (API 33+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    Log.d(Constants.TAG, "Permiso de notificaciones ya concedido")
                }
                else -> {
                    Log.d(Constants.TAG, "Solicitando permiso de notificaciones")
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
        
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
                MainContent(
                    navController = navController,
                    shouldNavigateToCart = shouldNavigateToCart,
                    onNavigationHandled = { shouldNavigateToCart = false }
                )
            }
        }
    }
    
    override fun onPause() {
        super.onPause()
        
        // Verificar si hay items en el carrito cuando la app pierde foco
        cartViewModel?.let { viewModel ->
            lifecycleScope.launch {
                // Obtener el valor actual del StateFlow
                val totalItems = viewModel.totalItems.value
                Log.d(Constants.TAG, "onPause - Total items en carrito: $totalItems")
                
                if (totalItems > 0) {
                    Log.d(Constants.TAG, "Mostrando notificación de carrito abandonado")
                    // Mostrar notificación inmediatamente si hay items en el carrito
                    NotificationHelper.showCartReminderNotification(this@MainActivity, totalItems)
                } else {
                    Log.d(Constants.TAG, "No hay items en el carrito, no se muestra notificación")
                }
            }
        }
    }
    
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleNotificationNavigation(intent)
    }
    
    /**
     * Maneja la navegación cuando la app se abre desde una notificación
     */
    private fun handleNotificationNavigation(intent: Intent?) {
        val navigateTo = intent?.getStringExtra("navigate_to")
        val action = intent?.action
        
        if (navigateTo == "cart" || action == "com.example.milsaborestest.NAVIGATE_TO_CART") {
            Log.d(Constants.TAG, "Intent de notificación detectado: navegar al carrito")
            shouldNavigateToCart = true
        }
    }
}
