package com.example.milsaborestest.presentation.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.milsaborestest.domain.model.Category
import com.example.milsaborestest.ui.theme.CardWhite
import com.example.milsaborestest.util.Constants.Design

@Composable
fun CategoryCard(
    category: Category,
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Animación de scale al presionar
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "categoryCardScale"
    )
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(Design.PADDING_SMALL)
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = androidx.compose.material.ripple.rememberRipple()
            ) { onCategoryClick(category.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = Design.CARD_ELEVATION),
        shape = RoundedCornerShape(Design.CARD_RADIUS),
        colors = CardDefaults.cardColors(containerColor = CardWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Design.PADDING_STANDARD),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = getIconVector(category.icono),
                contentDescription = category.nombre,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(Design.PADDING_SMALL))
            
            Text(
                text = category.nombre,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "${category.productos.size} productos",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

fun getIconVector(icono: String): androidx.compose.ui.graphics.vector.ImageVector {
    // Mapear iconos a Material Icons
    return when (icono) {
        "square" -> Icons.Filled.Square
        "circle" -> Icons.Filled.Circle
        "cookie" -> Icons.Filled.Restaurant
        "favorite" -> Icons.Filled.Favorite
        "home" -> Icons.Filled.Home
        "eco" -> Icons.Filled.Eco
        "park" -> Icons.Filled.Park
        "star" -> Icons.Filled.Star
        else -> Icons.Filled.Cake
    }
}

