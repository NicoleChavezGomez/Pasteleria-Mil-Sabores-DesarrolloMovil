package com.example.milsaborestest.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = BrandPink,
    secondary = BrandPinkDark,
    tertiary = Pink80,
    background = androidx.compose.ui.graphics.Color(0xFF1A1A1A),
    surface = androidx.compose.ui.graphics.Color(0xFF2D2D2D)
)

private val LightColorScheme = lightColorScheme(
    primary = BrandPink,
    secondary = BrandPinkDark,
    tertiary = Pink40,
    background = Color.White, // NEUTRO - Aplicar BackgroundPink solo donde se necesite
    surface = Color.White, // NEUTRO - Ya es CardWhite
    surfaceVariant = Color.White, // NEUTRO - Ya no BackgroundPink
    onSurface = TextDark,
    onSurfaceVariant = TextMedium,
    onPrimary = Color.White,
    onSecondary = Color.White,
    primaryContainer = BrandPinkLight,
    onPrimaryContainer = BrandPinkDark,
    error = Color(0xFFBA1A1A)
)

@Composable
fun MilSaboresTestTheme(
    darkTheme: Boolean = false, // SIEMPRE modo claro para pastelería
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Desactivado para usar los colores de marca
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}