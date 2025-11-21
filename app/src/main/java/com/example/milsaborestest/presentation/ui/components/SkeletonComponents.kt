package com.example.milsaborestest.presentation.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.milsaborestest.ui.theme.CardWhite
import com.example.milsaborestest.util.Constants.Design

/**
 * Efecto de shimmer que puede aplicarse a cualquier composable
 */
@Composable
fun ShimmerBrush(
    targetValue: Float = 1000f
): Brush {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    
    val shimmerTranslate by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerTranslate"
    )
    
    return Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
            MaterialTheme.colorScheme.surfaceVariant
        ),
        start = androidx.compose.ui.geometry.Offset(shimmerTranslate, shimmerTranslate),
        end = androidx.compose.ui.geometry.Offset(shimmerTranslate + 200f, shimmerTranslate + 200f)
    )
}

/**
 * Componente base de skeleton con shimmer
 */
@Composable
fun SkeletonBox(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(ShimmerBrush())
    )
}

/**
 * Skeleton para ProductCard
 */
@Composable
fun ProductCardSkeleton(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.heightIn(min = 280.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = Design.CARD_ELEVATION),
        shape = RoundedCornerShape(Design.CARD_RADIUS),
        colors = CardDefaults.cardColors(containerColor = CardWhite)
    ) {
        Column(
            modifier = Modifier
                .padding(Design.PADDING_MEDIUM)
                .fillMaxHeight()
        ) {
            // Imagen skeleton
            SkeletonBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(Design.BUTTON_RADIUS))
            )
            
            Spacer(modifier = Modifier.height(Design.PADDING_SMALL))
            
            // Título skeleton
            SkeletonBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp) // Altura para 2 líneas
            )
            
            Spacer(modifier = Modifier.height(Design.PADDING_SMALL))
            
            // Descripción skeleton
            SkeletonBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp) // Altura para 2 líneas
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Precio y rating skeleton
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
            ) {
                SkeletonBox(
                    modifier = Modifier
                        .width(80.dp)
                        .height(24.dp)
                )
                SkeletonBox(
                    modifier = Modifier
                        .width(60.dp)
                        .height(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(Design.PADDING_SMALL))
            
            // Botón skeleton
            SkeletonBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            )
        }
    }
}

/**
 * Skeleton para CategoryCard
 */
@Composable
fun CategoryCardSkeleton(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.size(100.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = Design.CARD_ELEVATION),
        shape = RoundedCornerShape(Design.CARD_RADIUS),
        colors = CardDefaults.cardColors(containerColor = CardWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Design.PADDING_MEDIUM),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            // Icono skeleton
            SkeletonBox(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(24.dp))
            )
            
            Spacer(modifier = Modifier.height(Design.PADDING_SMALL))
            
            // Texto skeleton
            SkeletonBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
            )
        }
    }
}

/**
 * Grid de skeletons para productos (6 items)
 */
@Composable
fun ProductGridSkeleton(
    itemCount: Int = 6,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = androidx.compose.foundation.layout.PaddingValues(Design.PADDING_SMALL),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(Design.PADDING_SMALL)
    ) {
        items(items = (0 until itemCount).chunked(2)) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(Design.PADDING_SMALL)
            ) {
                rowItems.forEach {
                    ProductCardSkeleton(modifier = Modifier.weight(1f))
                }
                // Espacio vacío si hay número impar de productos
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

/**
 * Row horizontal de skeletons para categorías
 */
@Composable
fun CategoryRowSkeleton(
    itemCount: Int = 5,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(Design.PADDING_SMALL)
    ) {
        items(itemCount) {
            CategoryCardSkeleton()
        }
    }
}

/**
 * Skeleton para ProductDetailScreen
 */
@Composable
fun ProductDetailSkeleton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Design.PADDING_STANDARD),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(Design.PADDING_STANDARD)
    ) {
        // Imagen principal
        SkeletonBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(Design.CARD_RADIUS))
        )
        
        // Título
        SkeletonBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )
        
        // Precio y rating
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
        ) {
            SkeletonBox(
                modifier = Modifier
                    .width(120.dp)
                    .height(28.dp)
            )
            SkeletonBox(
                modifier = Modifier
                    .width(80.dp)
                    .height(24.dp)
            )
        }
        
        // Botón
        SkeletonBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        )
        
        // Descripción
        repeat(3) {
            SkeletonBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(Design.PADDING_STANDARD))
        
        // Información
        SkeletonBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
        ) {
            SkeletonBox(
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
            )
            SkeletonBox(
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(Design.PADDING_STANDARD))
        
        // Ingredientes
        SkeletonBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )
        
        repeat(5) {
            SkeletonBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
        }
    }
}

/**
 * Skeleton para CartItem
 */
@Composable
fun CartItemSkeleton(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = Design.CARD_ELEVATION),
        colors = CardDefaults.cardColors(containerColor = CardWhite)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Design.PADDING_MEDIUM),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(Design.PADDING_MEDIUM)
        ) {
            // Imagen
            SkeletonBox(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(Design.CARD_RADIUS))
            )
            
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(Design.PADDING_SMALL)
            ) {
                // Nombre
                SkeletonBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                )
                // Precio
                SkeletonBox(
                    modifier = Modifier
                        .width(80.dp)
                        .height(16.dp)
                )
            }
            
            // Controles de cantidad
            Column(
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(Design.SPACING_XSMALL)
            ) {
                SkeletonBox(
                    modifier = Modifier.size(32.dp)
                )
                SkeletonBox(
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

/**
 * Lista de skeletons para CartScreen
 */
@Composable
fun CartListSkeleton(
    itemCount: Int = 3,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = androidx.compose.foundation.layout.PaddingValues(Design.PADDING_STANDARD),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(Design.PADDING_SMALL)
    ) {
        items(itemCount) {
            CartItemSkeleton()
        }
    }
}

