package com.example.milsaborestest.presentation.navigation

/**
 * Definición de rutas de navegación
 */
sealed class Screen(val route: String) {
    // Bottom Navigation Screens
    object Home : Screen("home")
    object Products : Screen("products") {
        fun createRoute(categoryId: String? = null) = if (categoryId != null) "products?categoryId=$categoryId" else "products"
    }
    object Cart : Screen("cart")
    object Account : Screen("account")
    
    // Secondary Screens
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
    object Login : Screen("login")
    object Register : Screen("register")
    object Splash : Screen("splash")
}

