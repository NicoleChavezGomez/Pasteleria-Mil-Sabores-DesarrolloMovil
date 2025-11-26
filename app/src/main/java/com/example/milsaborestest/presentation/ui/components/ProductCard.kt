package com.example.milsaborestest.presentation.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.milsaborestest.domain.model.Product
import com.example.milsaborestest.ui.theme.CardWhite
import com.example.milsaborestest.util.Constants.Design
import com.example.milsaborestest.util.formatPrice
import com.example.milsaborestest.util.formatRating
import kotlinx.coroutines.delay

@Composable
fun ProductCard(
    product: Product,
    onProductClick: (String) -> Unit,
    onAddToCart: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    // Estado para animación de éxito
    var showSuccess by remember { mutableStateOf(false) }
    
    // Animación de scale para la card completa (hover effect sutil)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "cardScale"
    )
    
    Card(
        modifier = modifier
            .heightIn(min = 280.dp)
            .scale(scale), // Aplicar animación de scale
        elevation = CardDefaults.cardElevation(defaultElevation = Design.CARD_ELEVATION),
        shape = RoundedCornerShape(Design.CARD_RADIUS),
        colors = CardDefaults.cardColors(containerColor = CardWhite)
    ) {
        Column(modifier = Modifier
            .padding(Design.PADDING_MEDIUM)
            .fillMaxHeight()) {
            // Imagen clickeable con animación
            AsyncImage(
                model = product.imagen,
                contentDescription = product.nombre,
                placeholder = painterResource(R.drawable.ic_product_default),
                error = painterResource(R.drawable.ic_product_default),
                fallback = painterResource(R.drawable.ic_product_default),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(Design.BUTTON_RADIUS))
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { onProductClick(product.id) },
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.height(Design.PADDING_SMALL))
            
            // Título clickeable con altura fija
            Text(
                text = product.nombre,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                modifier = Modifier
                    .clickable { onProductClick(product.id) }
                    .height(48.dp) // Altura fija para 2 líneas
            )
            
            Spacer(modifier = Modifier.height(Design.SPACING_XSMALL))
            
            // Descripción con altura fija
            Text(
                text = product.descripcion,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.height(36.dp) // Altura fija para 2 líneas
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = product.precio.formatPrice(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "⭐ ${product.rating.formatRating()}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                
                // Botón agregar al carrito con animación
                onAddToCart?.let { addToCart ->
                    AnimatedAddToCartButton(
                        showSuccess = showSuccess,
                        onClick = {
                            addToCart(product.id)
                            showSuccess = true
                        }
                    )
                    
                    // Reset del estado de éxito después de la animación
                    LaunchedEffect(showSuccess) {
                        if (showSuccess) {
                            delay(1000)
                            showSuccess = false
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AnimatedAddToCartButton(
    showSuccess: Boolean,
    onClick: () -> Unit
) {
    val buttonInteractionSource = remember { MutableInteractionSource() }
    val isButtonPressed by buttonInteractionSource.collectIsPressedAsState()
    
    // Animación de scale para el botón
    val buttonScale by animateFloatAsState(
        targetValue = when {
            showSuccess -> 1.2f
            isButtonPressed -> 0.85f
            else -> 1f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "buttonScale"
    )
    
    // Animación de rotación para el check
    val rotation by animateFloatAsState(
        targetValue = if (showSuccess) 360f else 0f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "checkRotation"
    )
    
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .scale(buttonScale)
            .background(
                if (showSuccess) MaterialTheme.colorScheme.tertiary 
                else MaterialTheme.colorScheme.primary,
                RoundedCornerShape(Design.BUTTON_RADIUS)
            ),
        interactionSource = buttonInteractionSource
    ) {
        androidx.compose.animation.Crossfade(
            targetState = showSuccess,
            animationSpec = tween(durationMillis = 300),
            label = "iconCrossfade"
        ) { success ->
            if (success) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = "Agregado",
                    tint = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier.graphicsLayer { rotationZ = rotation }
                )
            } else {
                Icon(
                    Icons.Filled.AddShoppingCart,
                    contentDescription = "Agregar al carrito",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

