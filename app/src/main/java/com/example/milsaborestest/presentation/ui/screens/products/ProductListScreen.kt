package com.example.milsaborestest.presentation.ui.screens.products

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.milsaborestest.presentation.ui.components.LoadingIndicator
import com.example.milsaborestest.presentation.ui.components.ProductCard
import com.example.milsaborestest.presentation.viewmodel.CartViewModel
import com.example.milsaborestest.presentation.viewmodel.ProductViewModel
import com.example.milsaborestest.ui.theme.CardWhite
import com.example.milsaborestest.ui.theme.TextDark
import com.example.milsaborestest.util.Constants.Design
import com.example.milsaborestest.util.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    categoryId: String,
    onProductClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onNavigateToCart: () -> Unit,
    productViewModel: ProductViewModel = viewModel(),
    cartViewModel: CartViewModel
) {
    // Cargar productos cuando se cambia la categoría
    LaunchedEffect(categoryId) {
        productViewModel.loadProductsByCategory(categoryId)
    }
    
    val productsState by productViewModel.productsByCategoryState.collectAsState()
    val totalItems by cartViewModel.totalItems.collectAsState()
    
    // Guardar valor local para evitar smart cast issues
    val currentState = productsState
    
    val addToCart = { productId: String ->
        cartViewModel.addToCart(productId, 1)
    }
    
    Column(modifier = Modifier.fillMaxSize()) {
        // TopBar
        Surface(
            color = CardWhite,
            shadowElevation = Design.CARD_ELEVATION
        ) {
            TopAppBar(
                title = { Text("Productos") },
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
                    LoadingIndicator(modifier = Modifier.fillMaxSize())
                }
                is UiState.Success -> {
                    if (currentState.data.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No hay productos en esta categoría",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(Design.PADDING_STANDARD),
                            verticalArrangement = Arrangement.spacedBy(Design.PADDING_SMALL)
                        ) {
                            items(currentState.data) { product ->
                                ProductCard(
                                    product = product,
                                    onProductClick = onProductClick,
                                    onAddToCart = addToCart
                                )
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

