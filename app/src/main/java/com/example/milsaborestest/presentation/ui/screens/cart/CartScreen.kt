package com.example.milsaborestest.presentation.ui.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.milsaborestest.presentation.ui.components.LoadingIndicator
import com.example.milsaborestest.presentation.viewmodel.CartViewModel
import com.example.milsaborestest.ui.theme.CardWhite
import com.example.milsaborestest.ui.theme.TextDark
import com.example.milsaborestest.util.Constants.Design
import com.example.milsaborestest.util.formatPrice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onBackClick: () -> Unit,
    viewModel: CartViewModel
) {
    val cartItems by viewModel.cartItems.collectAsState()
    val totalItems by viewModel.totalItems.collectAsState()
    val totalPrice by viewModel.totalPrice.collectAsState()
    
    Column(modifier = Modifier.fillMaxSize()) {
        // TopBar
        Surface(
            color = CardWhite,
            shadowElevation = Design.CARD_ELEVATION
        ) {
            TopAppBar(
                title = { Text("Carrito") },
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
        if (cartItems.isEmpty()) {
            EmptyCart(modifier = Modifier.weight(1f))
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(Design.PADDING_STANDARD),
                verticalArrangement = Arrangement.spacedBy(Design.PADDING_SMALL)
            ) {
                items(cartItems) { item ->
                    CartItemRow(
                        cartItem = item,
                        onIncreaseQuantity = {
                            viewModel.updateQuantity(item.id, item.cantidad + 1)
                        },
                        onDecreaseQuantity = {
                            if (item.cantidad > 1) {
                                viewModel.updateQuantity(item.id, item.cantidad - 1)
                            } else {
                                viewModel.removeFromCart(item.id)
                            }
                        },
                        onRemoveItem = {
                            viewModel.removeFromCart(item.id)
                        }
                    )
                }
            }
            
            // Bottom bar fijo
            Surface(
                modifier = Modifier.fillMaxWidth(),
                tonalElevation = 8.dp,
                color = CardWhite
            ) {
                Column(
                    modifier = Modifier.padding(Design.PADDING_STANDARD),
                    verticalArrangement = Arrangement.spacedBy(Design.PADDING_MEDIUM)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total:",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = totalPrice.formatPrice(),
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Design.PADDING_SMALL)
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.clearCart() },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Vaciar Carrito")
                        }
                        
                        Button(
                            onClick = { /* TODO: Implementar checkout */ },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Comprar")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    cartItem: com.example.milsaborestest.domain.model.CartItem,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    onRemoveItem: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = Design.CARD_ELEVATION),
        colors = CardDefaults.cardColors(containerColor = CardWhite)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Design.PADDING_MEDIUM),
            horizontalArrangement = Arrangement.spacedBy(Design.PADDING_MEDIUM)
        ) {
            AsyncImage(
                model = cartItem.imagen,
                contentDescription = cartItem.nombre,
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop
            )
            
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Design.SPACING_XSMALL)
            ) {
                Text(
                    text = cartItem.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2
                )
                
                Text(
                    text = cartItem.precio.formatPrice(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Design.PADDING_SMALL)
                ) {
                    IconButton(
                        onClick = onDecreaseQuantity,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Text("-", style = MaterialTheme.typography.titleLarge)
                    }
                    
                    Text(
                        text = "${cartItem.cantidad}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.widthIn(min = 24.dp)
                    )
                    
                    IconButton(
                        onClick = onIncreaseQuantity,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Text("+", style = MaterialTheme.typography.titleLarge)
                    }
                }
            }
            
            IconButton(onClick = onRemoveItem) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Eliminar",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
        
        HorizontalDivider()
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Design.PADDING_MEDIUM, vertical = Design.PADDING_SMALL),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Subtotal:",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = cartItem.subtotal.formatPrice(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun EmptyCart(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Design.PADDING_STANDARD)
        ) {
            Icon(
                Icons.Filled.ShoppingCart,
                contentDescription = "Carrito vacío",
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Tu carrito está vacío",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Agrega algunos productos para comenzar",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

