package com.example.milsaborestest.presentation.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.milsaborestest.domain.model.Category
import com.example.milsaborestest.presentation.navigation.Screen
import com.example.milsaborestest.presentation.ui.components.AppFooter
import com.example.milsaborestest.presentation.ui.components.CategoryCard
import com.example.milsaborestest.presentation.ui.components.CategoryRowSkeleton
import com.example.milsaborestest.presentation.ui.components.ProductCard
import com.example.milsaborestest.presentation.ui.components.ProductCardSkeleton
import com.example.milsaborestest.presentation.ui.components.ProductCarousel
import com.example.milsaborestest.presentation.ui.components.ProductCarouselItem
import com.example.milsaborestest.presentation.viewmodel.CartViewModel
import com.example.milsaborestest.presentation.viewmodel.ProductViewModel
import com.example.milsaborestest.R
import com.example.milsaborestest.ui.theme.CardWhite
import com.example.milsaborestest.util.Constants
import com.example.milsaborestest.util.Constants.Design
import com.example.milsaborestest.util.UiState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    val productViewModel: ProductViewModel = viewModel()
    val categoriesState by productViewModel.categoriesState.collectAsState()
    val featuredProductsState by productViewModel.featuredProductsState.collectAsState()
    val totalItems by cartViewModel.totalItems.collectAsState()
    
    // Secciones colapsables
    var categoriesExpanded by remember { mutableStateOf(true) }
    
    val addToCart = { productId: String ->
        cartViewModel.addToCart(productId, 1)
    }
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.spacedBy(Design.PADDING_SMALL)
    ) {
        // Carrusel grande (50% de pantalla)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                val carouselItems = Constants.CAROUSEL_IMAGES.map { carousel ->
                    ProductCarouselItem(
                        productId = carousel.productId,
                        image = carousel.drawableRes,
                        title = carousel.title
                    )
                }
                ProductCarousel(
                    items = carouselItems,
                    onItemClick = { productId ->
                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                    }
                )
            }
        }
        
        // Sección colapsable: Categorías
        item {
            ExpandableSection(
                title = "Categorías",
                expanded = categoriesExpanded,
                onExpandedChange = { categoriesExpanded = it },
                content = {
                    CategoriesSection(
                        categoriesState = categoriesState,
                        onCategoryClick = { categoryId ->
                            navController.navigate(Screen.Products.createRoute(categoryId))
                        }
                    )
                }
            )
        }
        
        // Productos Destacados como swiper horizontal
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Design.PADDING_STANDARD, vertical = Design.PADDING_SMALL),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Productos Destacados",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = { navController.navigate(Screen.Products.createRoute()) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Ver todos")
                }
            }
        }
        
        item {
            featuredProductsState.let { currentState ->
                when (currentState) {
                    is UiState.Loading -> {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(Design.PADDING_SMALL),
                            contentPadding = PaddingValues(horizontal = Design.PADDING_STANDARD)
                        ) {
                            items(5) {
                                ProductCardSkeleton(modifier = Modifier.width(180.dp))
                            }
                        }
                    }
                    is UiState.Success -> {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(Design.PADDING_SMALL),
                            contentPadding = PaddingValues(horizontal = Design.PADDING_STANDARD)
                        ) {
                            items(currentState.data) { product ->
                                ProductCard(
                                    product = product,
                                    onProductClick = { productId ->
                                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                                    },
                                    onAddToCart = addToCart,
                                    modifier = Modifier.width(200.dp)
                                )
                            }
                        }
                    }
                    is UiState.Error -> {
                        Text(
                            text = "Error: ${currentState.message}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Design.PADDING_STANDARD)
                        )
                    }
                }
            }
        }
        
        // Sección Sobre Nosotros
        item {
            Spacer(modifier = Modifier.height(Design.SPACING_LARGE))
            Text(
                text = "Sobre Nosotros",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Design.PADDING_STANDARD)
            )
        }
        
        item {
            AboutContent()
        }
        
        // Footer
        item {
            AppFooter()
        }
    }
}

@Composable
fun ExpandableSection(
    title: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    trailingButton: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    // Animación de rotación para el icono
    val rotationAngle by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = androidx.compose.animation.core.spring(
            dampingRatio = androidx.compose.animation.core.Spring.DampingRatioMediumBouncy,
            stiffness = androidx.compose.animation.core.Spring.StiffnessMedium
        ),
        label = "iconRotation"
    )
    
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Design.PADDING_STANDARD, vertical = Design.PADDING_SMALL),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onExpandedChange(!expanded) }
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(Design.PADDING_SMALL))
                Icon(
                    Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) "Colapsar" else "Expandir",
                    modifier = Modifier.graphicsLayer {
                        rotationZ = rotationAngle
                    }
                )
            }
            trailingButton?.invoke()
        }
        
        // Contenido con animación
        androidx.compose.animation.AnimatedVisibility(
            visible = expanded,
            enter = androidx.compose.animation.expandVertically(
                animationSpec = androidx.compose.animation.core.spring(
                    dampingRatio = androidx.compose.animation.core.Spring.DampingRatioMediumBouncy,
                    stiffness = androidx.compose.animation.core.Spring.StiffnessMedium
                )
            ) + androidx.compose.animation.fadeIn(
                animationSpec = androidx.compose.animation.core.tween(durationMillis = 300)
            ),
            exit = androidx.compose.animation.shrinkVertically(
                animationSpec = androidx.compose.animation.core.tween(durationMillis = 200)
            ) + androidx.compose.animation.fadeOut(
                animationSpec = androidx.compose.animation.core.tween(durationMillis = 200)
            )
        ) {
            content()
        }
    }
}

@Composable
fun ProductsGrid(
    products: List<com.example.milsaborestest.domain.model.Product>,
    onProductClick: (String) -> Unit,
    onAddToCart: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Design.PADDING_SMALL)
    ) {
        products.chunked(2).forEach { rowProducts ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Design.PADDING_SMALL)
            ) {
                rowProducts.forEach { product ->
                    ProductCard(
                        product = product,
                        onProductClick = onProductClick,
                        onAddToCart = onAddToCart,
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

@Composable
fun CategoriesSection(
    categoriesState: UiState<List<Category>>,
    onCategoryClick: (String) -> Unit
) {
    androidx.compose.animation.Crossfade(
        targetState = categoriesState,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 300),
        label = "categoriesStateCrossfade"
    ) { currentState ->
        when (currentState) {
            is UiState.Loading -> {
                CategoryRowSkeleton(itemCount = 5)
            }
            is UiState.Success -> {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(Design.PADDING_SMALL)
                ) {
                    items(currentState.data) { category ->
                        CategoryCard(
                            category = category,
                            onCategoryClick = onCategoryClick,
                            modifier = Modifier.width(150.dp)
                        )
                    }
                }
            }
            is UiState.Error -> {
                Text(
                    text = "Error: ${currentState.message}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun AboutContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Design.PADDING_STANDARD),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Design.SPACING_LARGE)
    ) {
        // Descripción
        Text(
            text = "Con más de 10 años de experiencia en el mundo de la pastelería, " +
                    "Mil Sabores se ha consolidado como una de las pastelerías más " +
                    "reconocidas de la región. Nuestro compromiso es ofrecer productos " +
                    "de la más alta calidad, elaborados con ingredientes frescos y " +
                    "técnicas artesanales tradicionales.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(horizontal = Design.PADDING_SMALL)
        )
        
        // Imagen del logo
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            shape = RoundedCornerShape(Design.CARD_RADIUS),
            elevation = CardDefaults.cardElevation(defaultElevation = Design.LOGIN_CARD_ELEVATION),
            colors = CardDefaults.cardColors(containerColor = CardWhite)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surface,
                                MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_milsabores),
                    contentDescription = "Mil Sabores - Pastelería Artesanal",
                    modifier = Modifier.size(150.dp)
                )
            }
        }
        
        // ¿Por qué elegirnos?
        Text(
            text = "¿Por qué elegirnos?",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        HorizontalDivider(
            modifier = Modifier.width(40.dp),
            thickness = 3.dp,
            color = MaterialTheme.colorScheme.primary
        )
        
        // Feature Cards
        AboutFullFeatureCard(
            icon = Icons.Filled.Favorite,
            title = "Hecho con Amor",
            description = "Cada producto está elaborado con dedicación y cariño, transmitiendo el sabor del hogar en cada bocado."
        )
        
        AboutFullFeatureCard(
            icon = Icons.Filled.LocalFlorist,
            title = "Ingredientes Frescos",
            description = "Utilizamos solo los mejores ingredientes naturales y frescos, seleccionados cuidadosamente para cada receta."
        )
        
        AboutFullFeatureCard(
            icon = Icons.Filled.Star,
            title = "Calidad Premium",
            description = "Nuestros productos superan las expectativas de nuestros clientes, garantizando una experiencia única."
        )
        
        // Estadísticas
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Design.PADDING_STANDARD)
        ) {
            AboutStatCard(
                number = "10+",
                label = "Años Experiencia",
                modifier = Modifier.weight(1f)
            )
            AboutStatCard(
                number = "500+",
                label = "Clientes Felices",
                modifier = Modifier.weight(1f)
            )
        }
        
        AboutStatCard(
            number = "50+",
            label = "Productos Únicos",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun AboutFullFeatureCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Design.CARD_RADIUS),
        elevation = CardDefaults.cardElevation(defaultElevation = Design.CARD_ELEVATION),
        colors = CardDefaults.cardColors(containerColor = CardWhite)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Design.PADDING_LARGE),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(Design.PADDING_STANDARD)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
                            )
                        ),
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = androidx.compose.ui.graphics.Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Design.PADDING_SMALL)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun AboutFeatureCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = Design.CARD_ELEVATION),
        colors = CardDefaults.cardColors(containerColor = CardWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Design.PADDING_STANDARD),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Design.PADDING_SMALL)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AboutStatCard(
    number: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = Design.CARD_ELEVATION),
        colors = CardDefaults.cardColors(containerColor = CardWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Design.PADDING_LARGE),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Design.PADDING_SMALL)
        ) {
            Text(
                text = number,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

