package com.example.milsaborestest.presentation.ui.components

import androidx.compose.animation.core.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay

/**
 * Wrapper para animar la entrada de items en listas
 * Aplica un efecto de fade-in y slide-in con delay escalonado
 */
@Composable
fun AnimatedListItem(
    index: Int,
    delayPerItem: Int = 50,
    content: @Composable () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay((index * delayPerItem).toLong())
        visible = true
    }
    
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            )
        ) + slideInVertically(
            initialOffsetY = { it / 2 },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )
    ) {
        content()
    }
}

/**
 * Modificador para agregar efecto de hover/press a elementos interactivos
 */
@Composable
fun Modifier.animatedHover(
    enabled: Boolean = true,
    scaleOnPress: Float = 0.95f
): Modifier {
    var isPressed by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) scaleOnPress else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "hoverScale"
    )
    
    return this.graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
}

/**
 * Animación de pulsación para botones y elementos clickeables
 */
@Composable
fun rememberPulseAnimation(
    enabled: Boolean = true,
    minScale: Float = 0.95f,
    maxScale: Float = 1.05f,
    durationMillis: Int = 1000
): Float {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    
    val scale by infiniteTransition.animateFloat(
        initialValue = minScale,
        targetValue = maxScale,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )
    
    return if (enabled) scale else 1f
}
