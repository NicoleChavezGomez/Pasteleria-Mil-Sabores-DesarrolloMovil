package com.example.milsaborestest.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.milsaborestest.domain.model.Product
import com.example.milsaborestest.ui.theme.CardWhite
import com.example.milsaborestest.util.Constants.Design
import com.example.milsaborestest.util.formatPrice
import com.example.milsaborestest.util.formatRating

@Composable
fun ProductCard(
    product: Product,
    onProductClick: (String) -> Unit,
    onAddToCart: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .heightIn(min = 280.dp), // Altura mínima para consistencia visual
        elevation = CardDefaults.cardElevation(defaultElevation = Design.CARD_ELEVATION),
        shape = RoundedCornerShape(Design.CARD_RADIUS),
        colors = CardDefaults.cardColors(containerColor = CardWhite)
    ) {
        Column(modifier = Modifier
            .padding(Design.PADDING_MEDIUM)
            .fillMaxHeight()) {
            // Imagen clickeable
            AsyncImage(
                model = product.imagen,
                contentDescription = product.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(Design.BUTTON_RADIUS))
                    .clickable { onProductClick(product.id) },
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
                
                // Botón agregar al carrito
                onAddToCart?.let { addToCart ->
                    IconButton(
                        onClick = { addToCart(product.id) },
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(Design.BUTTON_RADIUS)
                            )
                    ) {
                        Icon(
                            Icons.Filled.AddShoppingCart,
                            contentDescription = "Agregar al carrito",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

