package com.example.milsaborestest.presentation.ui.screens.productdetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.milsaborestest.presentation.ui.components.ProductDetailSkeleton
import com.example.milsaborestest.presentation.viewmodel.CartViewModel
import com.example.milsaborestest.presentation.viewmodel.ProductViewModel
import com.example.milsaborestest.ui.theme.CardWhite
import com.example.milsaborestest.ui.theme.TextDark
import com.example.milsaborestest.util.UiState
import com.example.milsaborestest.util.formatPrice
import com.example.milsaborestest.util.Constants.Design
import com.example.milsaborestest.util.formatRating

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: String,
    onBackClick: () -> Unit,
    onNavigateToCart: () -> Unit,
    productViewModel: ProductViewModel = viewModel(),
    cartViewModel: CartViewModel
) {
    // Cargar producto cuando cambia el ID
    LaunchedEffect(productId) {
        productViewModel.loadProductById(productId)
    }
    
    val productState by productViewModel.productDetailState.collectAsState()
    val totalItems by cartViewModel.totalItems.collectAsState()
    
    // Guardar valor local para evitar smart cast issues
    val currentState = productState
    
    Column(modifier = Modifier.fillMaxSize()) {
        // TopBar
        Surface(
            color = CardWhite,
            shadowElevation = Design.CARD_ELEVATION
        ) {
            TopAppBar(
                title = { Text("Detalle del Producto") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CardWhite,
                    titleContentColor = TextDark
                )
            )
        }
        
        // Contenido
        Box(modifier = Modifier.weight(1f)) {
            when (currentState) {
                is UiState.Loading -> {
                    ProductDetailSkeleton()
                }
                is UiState.Success -> {
                    val product = currentState.data
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                    // Imagen del producto
                    AsyncImage(
                        model = product.imagen,
                        contentDescription = product.nombre,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )
                    
                    Column(
                        modifier = Modifier.padding(Design.PADDING_STANDARD),
                        verticalArrangement = Arrangement.spacedBy(Design.PADDING_STANDARD)
                    ) {
                        // Nombre
                        Text(
                            text = product.nombre,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        // Precio y Rating
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = product.precio.formatPrice(),
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Filled.Star,
                                    contentDescription = "Rating",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = product.rating.formatRating(),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = " (${product.reviews})",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        
                        // Botón agregar al carrito
                        Button(
                            onClick = {
                                cartViewModel.addToCart(productId, 1)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Icon(
                                Icons.Filled.AddShoppingCart,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(Design.PADDING_SMALL))
                            Text("Agregar al Carrito")
                        }
                        
                        // Descripción corta
                        Text(
                            text = product.descripcion,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        
                        HorizontalDivider()
                        
                        // Descripción detallada
                        Text(
                            text = "Descripción",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = product.descripcionDetallada,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        
                        HorizontalDivider()
                        
                        // Información adicional
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            InfoItem("Porciones", product.porciones)
                            InfoItem("Calorías", product.calorias)
                        }
                        
                        HorizontalDivider()
                        
                        // Ingredientes
                        Text(
                            text = "Ingredientes",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = product.ingredientes,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        
                        // Reseñas
                        if (product.reseñas.isNotEmpty()) {
                            HorizontalDivider()
                            Text(
                                text = "Reseñas (${product.reseñas.size})",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            product.reseñas.forEach { review ->
                                ReviewItem(review = review)
                            }
                        }
                    }
                }
            }
            is UiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error: ${currentState.message}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
        
        // FAB overlay
            FloatingActionButton(
                onClick = onNavigateToCart,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(Design.PADDING_STANDARD),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            BadgedBox(badge = {
                if (totalItems > 0) {
                    Badge {
                        Text(text = if (totalItems > 99) "99+" else totalItems.toString())
                    }
                }
            }) {
                Icon(
                    Icons.Filled.ShoppingCart,
                    contentDescription = "Ver carrito"
                )
            }
        }
        }
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ReviewItem(review: com.example.milsaborestest.domain.model.Review) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Design.PADDING_SMALL),
        elevation = CardDefaults.cardElevation(defaultElevation = Design.CARD_ELEVATION),
        colors = CardDefaults.cardColors(containerColor = CardWhite)
    ) {
        Column(modifier = Modifier.padding(Design.PADDING_MEDIUM)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = review.autor,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Row {
                    repeat(review.rating) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = "Star",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
            Text(
                text = review.fecha,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(Design.SPACING_XSMALL))
            Text(
                text = review.comentario,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
