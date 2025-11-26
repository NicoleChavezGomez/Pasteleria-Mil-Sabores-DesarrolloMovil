package com.example.milsaborestest.presentation.ui.screens.products

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.milsaborestest.presentation.navigation.Screen
import com.example.milsaborestest.presentation.ui.components.ProductCard
import com.example.milsaborestest.presentation.ui.components.ProductFilters
import com.example.milsaborestest.presentation.ui.components.SortOption
import com.example.milsaborestest.presentation.viewmodel.CartViewModel
import com.example.milsaborestest.presentation.viewmodel.ProductViewModel
import com.example.milsaborestest.ui.theme.CardWhite
import com.example.milsaborestest.ui.theme.TextDark
import com.example.milsaborestest.util.Constants.Design
import com.example.milsaborestest.util.UiState
import com.example.milsaborestest.util.formatPrice
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AllProductsScreen(
    navController: NavHostController,
    initialCategoryId: String? = null
) {
    val productViewModel: ProductViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()
    val filteredProducts by productViewModel.filteredProducts.collectAsState()
    val categoriesState by productViewModel.categoriesState.collectAsState()
    val searchTerm by productViewModel.searchTerm.collectAsState()
    val selectedCategoryId by productViewModel.selectedCategoryId.collectAsState()
    val minPrice by productViewModel.minPrice.collectAsState()
    val maxPrice by productViewModel.maxPrice.collectAsState()
    val minPriceDefault by productViewModel.minPriceDefault.collectAsState()
    val maxPriceDefault by productViewModel.maxPriceDefault.collectAsState()
    val sortBy by productViewModel.sortBy.collectAsState()
    val totalItems by cartViewModel.totalItems.collectAsState()
    
    var showFilters by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    
    // Aplicar filtro inicial de categoría si se proporciona o limpiar filtros
    LaunchedEffect(initialCategoryId) {
        if (initialCategoryId != null) {
            productViewModel.setSelectedCategory(initialCategoryId)
        } else {
            productViewModel.clearFilters()
        }
    }
    
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
                title = { Text("Todos los Productos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { showFilters = !showFilters }) {
                        Icon(
                            if (showFilters) Icons.Filled.Clear else Icons.Filled.FilterList,
                            contentDescription = if (showFilters) "Cerrar filtros" else "Ver filtros"
                        )
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
            Column(modifier = Modifier.fillMaxSize()) {
                // Filtros colapsables con animación
                androidx.compose.animation.AnimatedVisibility(
                    visible = showFilters,
                    enter = androidx.compose.animation.expandVertically(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    ) + androidx.compose.animation.fadeIn(
                        animationSpec = tween(durationMillis = 300)
                    ),
                    exit = androidx.compose.animation.shrinkVertically(
                        animationSpec = tween(durationMillis = 200)
                    ) + androidx.compose.animation.fadeOut(
                        animationSpec = tween(durationMillis = 200)
                    )
                ) {
                    when (val currentCategoriesState = categoriesState) {
                        is UiState.Loading -> {
                            // No mostrar nada mientras carga
                        }
                        is UiState.Success -> {
                            ProductFilters(
                                searchTerm = searchTerm,
                                onSearchChange = { productViewModel.setSearchTerm(it) },
                                categories = currentCategoriesState.data,
                                selectedCategoryId = selectedCategoryId,
                                onCategoryChange = { productViewModel.setSelectedCategory(it) },
                                minPrice = minPrice,
                                maxPrice = maxPrice,
                                minPriceDefault = minPriceDefault,
                                maxPriceDefault = maxPriceDefault,
                                onMinPriceChange = { productViewModel.setMinPrice(it) },
                                onMaxPriceChange = { productViewModel.setMaxPrice(it) },
                                sortBy = sortBy,
                                onSortChange = { productViewModel.setSortBy(it) },
                                productsCount = filteredProducts.size,
                                onApplyFilters = {
                                    coroutineScope.launch {
                                        showFilters = false
                                        snackbarHostState.showSnackbar(
                                            message = "Filtros aplicados: ${filteredProducts.size} productos",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                },
                                modifier = Modifier.padding(Design.PADDING_SMALL)
                            )
                        }
                        is UiState.Error -> {
                            // Mostrar error si es necesario
                        }
                    }
                }
                
                // Chips de filtros activos
                when (val currentCategoriesState = categoriesState) {
                    is UiState.Success -> {
                        ActiveFiltersChips(
                            searchTerm = searchTerm,
                            onClearSearch = { productViewModel.setSearchTerm("") },
                            selectedCategoryId = selectedCategoryId,
                            selectedCategoryName = currentCategoriesState.data.find { it.id == selectedCategoryId }?.nombre,
                            onClearCategory = { productViewModel.setSelectedCategory(null) },
                            minPrice = minPrice,
                            maxPrice = maxPrice,
                            minPriceDefault = minPriceDefault,
                            maxPriceDefault = maxPriceDefault,
                            onClearPrice = { 
                                productViewModel.setMinPrice(minPriceDefault)
                                productViewModel.setMaxPrice(maxPriceDefault)
                            },
                            sortBy = sortBy,
                            onClearSort = { productViewModel.setSortBy(com.example.milsaborestest.presentation.ui.components.SortOption.DEFAULT) },
                            onClearAll = { productViewModel.clearFilters() },
                            modifier = Modifier.padding(horizontal = Design.PADDING_SMALL, vertical = Design.SPACING_XSMALL)
                        )
                    }
                    else -> { /* No mostrar chips mientras carga */ }
                }
                
                // Lista de productos
                if (filteredProducts.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(Design.PADDING_STANDARD)
                        ) {
                            Text(
                                text = "No se encontraron productos",
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Intenta ajustar los filtros",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(Design.PADDING_SMALL),
                        verticalArrangement = Arrangement.spacedBy(Design.PADDING_SMALL),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(filteredProducts.chunked(2)) { rowProducts ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(Design.PADDING_SMALL)
                            ) {
                                rowProducts.forEach { product ->
                                    ProductCard(
                                        product = product,
                                        onProductClick = { productId ->
                                            navController.navigate(Screen.ProductDetail.createRoute(productId))
                                        },
                                        onAddToCart = addToCart,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                // Espacio vacío si hay número impar de productos
                                if (rowProducts.size == 1) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }
            
            // FAB overlay
            FloatingActionButton(
                onClick = { navController.navigate(Screen.Cart.route) },
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
                    Icon(Icons.Filled.ShoppingCart, contentDescription = "Ver carrito")
                }
            }
            
            // SnackbarHost para feedback
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
                    .padding(bottom = 72.dp) // Evitar que tape el FAB
            )
        }
    }
}

@Composable
fun ActiveFiltersChips(
    searchTerm: String,
    onClearSearch: () -> Unit,
    selectedCategoryId: String?,
    selectedCategoryName: String?,
    onClearCategory: () -> Unit,
    minPrice: Int,
    maxPrice: Int,
    minPriceDefault: Int,
    maxPriceDefault: Int,
    onClearPrice: () -> Unit,
    sortBy: SortOption,
    onClearSort: () -> Unit,
    onClearAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hasPriceFilter = minPrice != minPriceDefault || maxPrice != maxPriceDefault
    val hasActiveFilters = searchTerm.isNotEmpty() || selectedCategoryId != null || hasPriceFilter || sortBy != SortOption.DEFAULT
    
    if (hasActiveFilters) {
        LazyRow(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Design.SPACING_XSMALL),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item {
                FilterChip(
                    selected = false,
                    onClick = onClearAll,
                    label = { Text("Limpiar todos") },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Clear,
                            contentDescription = "Limpiar todos",
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        labelColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                )
            }
            
            if (searchTerm.isNotEmpty()) {
                item {
                    FilterChip(
                        selected = true,
                        onClick = onClearSearch,
                        label = { Text("\"$searchTerm\"") },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Quitar búsqueda",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    )
                }
            }
            
            if (selectedCategoryId != null && selectedCategoryName != null) {
                item {
                    FilterChip(
                        selected = true,
                        onClick = onClearCategory,
                        label = { Text(selectedCategoryName) },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Quitar categoría",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    )
                }
            }
            
            if (hasPriceFilter) {
                item {
                    FilterChip(
                        selected = true,
                        onClick = onClearPrice,
                        label = { 
                            Text("${minPrice.formatPrice()} - ${maxPrice.formatPrice()}")
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Quitar precio",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    )
                }
            }
            
            if (sortBy != SortOption.DEFAULT) {
                item {
                    FilterChip(
                        selected = true,
                        onClick = onClearSort,
                        label = { Text(sortBy.label) },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Quitar orden",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    )
                }
            }
        }
    }
}

