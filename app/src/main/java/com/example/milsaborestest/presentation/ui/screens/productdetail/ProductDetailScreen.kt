package com.example.milsaborestest.presentation.ui.screens.productdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.milsaborestest.R
import com.example.milsaborestest.presentation.navigation.Screen
import com.example.milsaborestest.presentation.ui.components.ProductDetailSkeleton
import com.example.milsaborestest.presentation.viewmodel.AuthViewModel
import com.example.milsaborestest.presentation.viewmodel.CartViewModel
import com.example.milsaborestest.presentation.viewmodel.ProductViewModel
import com.example.milsaborestest.presentation.viewmodel.ReviewViewModel
import com.example.milsaborestest.ui.theme.CardWhite
import com.example.milsaborestest.ui.theme.TextDark
import com.example.milsaborestest.util.UiState
import com.example.milsaborestest.util.formatPrice
import com.example.milsaborestest.util.Constants.Design
import com.example.milsaborestest.util.formatRating

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavHostController,
    productId: String,
    authViewModel: AuthViewModel
) {
    val productViewModel: ProductViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()
    val reviewViewModel: ReviewViewModel = viewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    
    val currentUser by authViewModel.user.collectAsState()
    val isAuthenticated by authViewModel.isAuthenticated.collectAsState()
    val currentUserId = currentUser?.id?.toIntOrNull()
    // Cargar producto cuando cambia el ID
    LaunchedEffect(productId) {
        productViewModel.loadProductById(productId)
    }
    LaunchedEffect(productId, currentUserId) {
        reviewViewModel.loadReviews(productId, currentUserId)
    }
    LaunchedEffect(currentUserId) {
        reviewViewModel.refreshUserReviewStatus(currentUserId)
    }
    
    val productState by productViewModel.productDetailState.collectAsState()
    val totalItems by cartViewModel.totalItems.collectAsState()
    val reviews by reviewViewModel.reviews.collectAsState()
    val averageRating by reviewViewModel.averageRating.collectAsState()
    val reviewCount by reviewViewModel.reviewCount.collectAsState()
    val userHasReview by reviewViewModel.userHasReview.collectAsState()
    val isSubmittingReview by reviewViewModel.isSubmitting.collectAsState()
    val reviewMessage by reviewViewModel.message.collectAsState()
    
    var newReviewRating by rememberSaveable { mutableIntStateOf(5) }
    var newReviewComment by rememberSaveable { mutableStateOf("") }
    
    LaunchedEffect(reviewMessage) {
        reviewMessage?.let {
            snackbarHostState.showSnackbar(it)
            reviewViewModel.clearMessage()
        }
    }
    
    LaunchedEffect(userHasReview) {
        if (userHasReview) {
            newReviewComment = ""
            newReviewRating = 5
        }
    }
    
    // Guardar valor local para evitar smart cast issues
    val currentState = productState
    
    Scaffold(
        topBar = {
            Surface(
                color = CardWhite,
                shadowElevation = Design.CARD_ELEVATION
            ) {
                TopAppBar(
                    title = { Text("Detalle del Producto") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = CardWhite,
                        titleContentColor = TextDark
                    )
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.Cart.route) },
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
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentState) {
                is UiState.Loading -> ProductDetailSkeleton()
                is UiState.Success -> {
                    val product = currentState.data
                    val displayedRating = if (reviewCount > 0) averageRating else product.rating
                    val displayedReviews = if (reviewCount > 0) reviewCount else product.reviews
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                    // Imagen del producto
                    AsyncImage(
                        model = product.imagen,
                        contentDescription = product.nombre,
                        placeholder = painterResource(R.drawable.producto_default),
                        error = painterResource(R.drawable.producto_default),
                        fallback = painterResource(R.drawable.producto_default),
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
                                    text = displayedRating.formatRating(),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = " ($displayedReviews)",
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
                        HorizontalDivider()
                        Text(
                            text = "Comparte tu reseña",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        AddReviewSection(
                            isAuthenticated = isAuthenticated,
                            userHasReview = userHasReview,
                            rating = newReviewRating,
                            comment = newReviewComment,
                            onRatingChange = { newReviewRating = it },
                            onCommentChange = { newReviewComment = it },
                            onLoginRequired = { navController.navigate(Screen.Login.route) },
                            onSubmit = {
                                val userId = currentUserId
                                val userName = currentUser?.name ?: "Usuario"
                                if (userId != null && !isSubmittingReview) {
                                    reviewViewModel.addReview(
                                        productId = productId,
                                        userId = userId,
                                        authorName = userName,
                                        rating = newReviewRating,
                                        comment = newReviewComment
                                    )
                                }
                            },
                            isSubmitting = isSubmittingReview
                        )
                        
                        HorizontalDivider()
                        Text(
                            text = "Reseñas recientes",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        if (reviews.isEmpty()) {
                            Text(
                                text = "Aún no hay reseñas para este producto.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            reviews.forEach { review ->
                                ReviewItem(
                                    review = review,
                                    isCurrentUserReview = review.userId != null && review.userId == currentUserId
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
fun ReviewItem(
    review: com.example.milsaborestest.domain.model.Review,
    isCurrentUserReview: Boolean
) {
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
                    repeat(5) { index ->
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = "Star",
                            tint = if (index < review.rating) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            },
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
            if (isCurrentUserReview) {
                AssistChip(
                    onClick = {},
                    enabled = false,
                    colors = AssistChipDefaults.assistChipColors(
                        disabledLabelColor = MaterialTheme.colorScheme.primary
                    ),
                    label = { Text("Tu reseña") }
                )
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

@Composable
private fun AddReviewSection(
    isAuthenticated: Boolean,
    userHasReview: Boolean,
    rating: Int,
    comment: String,
    onRatingChange: (Int) -> Unit,
    onCommentChange: (String) -> Unit,
    onLoginRequired: () -> Unit,
    onSubmit: () -> Unit,
    isSubmitting: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = Design.CARD_ELEVATION),
        colors = CardDefaults.cardColors(containerColor = CardWhite)
    ) {
        Column(
            modifier = Modifier.padding(Design.PADDING_MEDIUM),
            verticalArrangement = Arrangement.spacedBy(Design.PADDING_MEDIUM)
        ) {
            when {
                !isAuthenticated -> {
                    Text(
                        text = "Inicia sesión para agregar tu reseña y ayudar a otros clientes.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Button(
                        onClick = onLoginRequired,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Iniciar sesión")
                    }
                }
                userHasReview -> {
                    Text(
                        text = "Gracias por compartir tu opinión.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                else -> {
                    Text(
                        text = "Calificación",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    RatingSelector(
                        rating = rating,
                        onRatingSelected = onRatingChange,
                        enabled = !isSubmitting
                    )
                    OutlinedTextField(
                        value = comment,
                        onValueChange = { if (it.length <= 250) onCommentChange(it) },
                        label = { Text("Escribe tu reseña (máx 250 caracteres)") },
                        enabled = !isSubmitting,
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 5,
                        colors = TextFieldDefaults.outlinedTextFieldColors()
                    )
                    Button(
                        onClick = onSubmit,
                        enabled = !isSubmitting && comment.isNotBlank(),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (isSubmitting) "Enviando..." else "Enviar reseña")
                    }
                }
            }
        }
    }
}

@Composable
private fun RatingSelector(
    rating: Int,
    onRatingSelected: (Int) -> Unit,
    enabled: Boolean
) {
    Row(horizontalArrangement = Arrangement.spacedBy(Design.PADDING_SMALL)) {
        (1..5).forEach { value ->
            IconButton(
                onClick = { onRatingSelected(value) },
                enabled = enabled
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Seleccionar $value estrellas",
                    tint = if (value <= rating) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
    }
}
