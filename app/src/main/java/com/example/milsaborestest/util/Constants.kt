package com.example.milsaborestest.util

import androidx.compose.ui.unit.dp

/**
 * Constantes de la aplicación
 */
object Constants {
    const val PRODUCTOS_JSON_FILE = "productos.json"
    const val TAG = "MilSabores"
    
    /**
     * Constantes de diseño para consistencia visual
     */
    object Design {
        // Border Radius
        val CARD_RADIUS = 12.dp
        val BUTTON_RADIUS = 8.dp
        val TEXTFIELD_RADIUS = 16.dp
        
        // Elevations
        val TOPBAR_ELEVATION = 4.dp
        val CARD_ELEVATION = 2.dp
        val LOGIN_CARD_ELEVATION = 4.dp
        
        // Padding estándar
        val PADDING_SMALL = 8.dp
        val PADDING_MEDIUM = 12.dp
        val PADDING_STANDARD = 16.dp
        val PADDING_LARGE = 20.dp
        val PADDING_EXTRA_LARGE = 24.dp
        
        // Spacing entre elementos
        val SPACING_XSMALL = 4.dp
        val SPACING_SMALL = 8.dp
        val SPACING_MEDIUM = 12.dp
        val SPACING_STANDARD = 16.dp
        val SPACING_LARGE = 24.dp
    }
    
    /**
     * Configuración del carrusel principal con imágenes locales
     */
    data class CarouselImage(
        val productId: String,
        val drawableRes: Int,
        val title: String
    )
    
    val CAROUSEL_IMAGES = listOf(
        CarouselImage("TE002", com.example.milsaborestest.R.drawable.carrusel1, "Torta Especial de Boda"),
        CarouselImage("PSA002", com.example.milsaborestest.R.drawable.carrusel2, "Cheesecake de Maracuyá Sin Azúcar"),
        CarouselImage("TT003", com.example.milsaborestest.R.drawable.carrusel3, "Torta Circular de Frutilla"),
        CarouselImage("TE001", com.example.milsaborestest.R.drawable.carrusel4, "Torta Especial de Cumpleaños")
    )
}

