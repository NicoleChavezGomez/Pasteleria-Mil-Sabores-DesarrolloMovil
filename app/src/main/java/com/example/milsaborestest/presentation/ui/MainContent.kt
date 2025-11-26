package com.example.milsaborestest.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import android.util.Log
import com.example.milsaborestest.presentation.viewmodel.SnackbarMessage
import com.example.milsaborestest.R
import com.example.milsaborestest.domain.model.User
import com.example.milsaborestest.presentation.navigation.AppNavigation
import com.example.milsaborestest.presentation.navigation.Screen
import com.example.milsaborestest.presentation.ui.components.BottomNavBar
import com.example.milsaborestest.presentation.ui.screens.account.ProfileImage
import com.example.milsaborestest.presentation.viewmodel.AuthViewModel
import com.example.milsaborestest.presentation.viewmodel.CartViewModel
import com.example.milsaborestest.ui.theme.BackgroundPink
import com.example.milsaborestest.ui.theme.BrandPink
import com.example.milsaborestest.ui.theme.CardWhite
import com.example.milsaborestest.ui.theme.TextDark
import com.example.milsaborestest.util.Constants
import com.example.milsaborestest.util.Constants.Design
import com.example.milsaborestest.util.formatPrice
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    navController: NavHostController,
    shouldNavigateToCart: Boolean = false,
    onNavigationHandled: () -> Unit = {}
) {
    val cartViewModel: CartViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()
    val totalItems by cartViewModel.totalItems.collectAsState()
    val totalPrice by cartViewModel.totalPrice.collectAsState()
    val isAuthenticated by authViewModel.isAuthenticated.collectAsState()
    val user by authViewModel.user.collectAsState()
    
    // Debug: Log del estado de autenticación
    Log.d(Constants.TAG, "MainContent - isAuthenticated: $isAuthenticated, user: ${user?.name}")
    // Sin protección de rutas - como PokeStore, puedes navegar sin autenticación
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    // Manejar navegación desde notificación
    LaunchedEffect(shouldNavigateToCart) {
        if (shouldNavigateToCart) {
            Log.d(Constants.TAG, "Navegando al carrito desde notificación")
            navController.navigate(Screen.Cart.route) {
                // Limpiar el back stack hasta la pantalla actual antes de navegar
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = false
                }
                launchSingleTop = true
            }
            onNavigationHandled()
        }
    }
    // DEBUG: Log para ver si cartViewModel está cambiando
    val snackbarMessageRaw = cartViewModel.snackbarMessage
    val snackbarMessage by snackbarMessageRaw.collectAsState()
    Log.d(Constants.TAG, "collectAsState - snackbarMessage value: $snackbarMessage")
    
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }
    
    val snackbarHostState = remember { SnackbarHostState() }
    
    // DEBUG: Log para ver si MainContent se está recomponiendo
    Log.d(Constants.TAG, "MainContent recomposing. snackbarMessage: ${snackbarMessage?.message}")
    
    // Mostrar Snackbar cuando hay mensaje - MOVIDO ANTES DEL DRAWER
    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let { message ->
            Log.d(Constants.TAG, "LaunchedEffect disparado! Mostrando Snackbar: ${message.message}")
            val result = snackbarHostState.showSnackbar(
                message = message.message,
                actionLabel = message.actionLabel,
                duration = if (message.actionLabel != null) SnackbarDuration.Short else SnackbarDuration.Long
            )
            Log.d(Constants.TAG, "showSnackbar result: $result")
            
            // Ejecutar acción si fue presionada
            if (result == SnackbarResult.ActionPerformed) {
                message.onAction?.invoke()
            }
            
            // Limpiar mensaje después de mostrarlo
            cartViewModel.clearSnackbarMessage()
        }
    }
    
    val showTopBar = when(currentRoute) {
        Screen.Home.route, Screen.Products.route, Screen.Cart.route, Screen.Account.route -> true
        else -> false
    }
    
    val showBottomBar = when(currentRoute) {
        Screen.Login.route, Screen.Register.route, Screen.Splash.route -> false
        else -> true
    }
    
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width((screenWidthDp * 0.75f).dp)
            ) {
                NavigationDrawerContent(
                    isAuthenticated = isAuthenticated,
                    user = user,
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it },
                    totalItems = totalItems,
                    onNavigateToLogin = {
                        coroutineScope.launch { drawerState.close() }
                        navController.navigate(Screen.Login.route)
                    },
                    onNavigateToHome = {
                        coroutineScope.launch { drawerState.close() }
                        navController.navigate(Screen.Home.route)
                    },
                    onNavigateToProducts = {
                        coroutineScope.launch { drawerState.close() }
                        navController.navigate("products")
                    },
                    onNavigateToCart = {
                        coroutineScope.launch { drawerState.close() }
                        navController.navigate(Screen.Cart.route)
                    },
                    onNavigateToAccount = {
                        coroutineScope.launch { drawerState.close() }
                        navController.navigate(Screen.Account.route)
                    },
                    onLogout = {
                        coroutineScope.launch { drawerState.close() }
                        authViewModel.logout()
                        // Redirigir a Login después de cerrar sesión
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    onCloseDrawer = {
                        coroutineScope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                modifier = Modifier.background(BackgroundPink),
                topBar = {
                    if (showTopBar) {
                        TopNavBar(
                            onMenuClick = { coroutineScope.launch { drawerState.open() } },
                            totalItems = totalItems,
                            totalPrice = totalPrice,
                            onCartClick = { navController.navigate(Screen.Cart.route) }
                        )
                    }
                },
                bottomBar = {
                    if (showBottomBar) {
                        BottomNavBar(navController = navController)
                    }
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    AppNavigation(
                        navController = navController,
                        authViewModel = authViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun TopNavBar(
    onMenuClick: () -> Unit,
    totalItems: Int,
    totalPrice: Int,
    onCartClick: () -> Unit
) {
    Surface(
        color = CardWhite,
        shadowElevation = Design.TOPBAR_ELEVATION,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Design.PADDING_SMALL, vertical = Design.PADDING_SMALL),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                androidx.compose.foundation.Image(
                    painter = painterResource(id = R.drawable.logo_milsabores),
                    contentDescription = "Mil Sabores Logo",
                    modifier = Modifier.size(70.dp)
                )
                Column {
                    Text(
                        "Mil Sabores",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        "Pastelería",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(Design.PADDING_SMALL),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                if (totalPrice > 0) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.padding(end = 4.dp)
                    ) {
                        Text(
                            text = totalPrice.formatPrice(),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                
                BadgedBox(badge = {
                    if (totalItems > 0) {
                        Badge {
                            Text(text = if (totalItems > 99) "99+" else totalItems.toString())
                        }
                    }
                }) {
                    IconButton(onClick = onCartClick) {
                        Icon(
                            Icons.Filled.ShoppingCart,
                            contentDescription = "Ver carrito",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                
                IconButton(onClick = onMenuClick) {
                    Icon(
                        Icons.Filled.Menu,
                        contentDescription = "Menú",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationDrawerContent(
    isAuthenticated: Boolean,
    user: User?,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    totalItems: Int,
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToProducts: () -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateToAccount: () -> Unit,
    onLogout: () -> Unit,
    onCloseDrawer: () -> Unit
) {
    Surface(color = androidx.compose.ui.graphics.Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            // Header con gradiente y botón cerrar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                androidx.compose.ui.graphics.Color.White,
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                            )
                        )
                    )
                    .padding(Design.PADDING_STANDARD)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Design.PADDING_SMALL)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_milsabores),
                            contentDescription = "Mil Sabores Logo",
                            modifier = Modifier.size(50.dp)
                        )
                        Column {
                            Text(
                                "Mil Sabores",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                "Pastelería",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    
                    IconButton(onClick = onCloseDrawer) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "Cerrar",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            )
            
            // Búsqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Design.PADDING_STANDARD),
                placeholder = { Text("Buscar") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchQueryChange("") }) {
                            Icon(Icons.Filled.Clear, contentDescription = "Limpiar")
                        }
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = androidx.compose.ui.graphics.Color.White,
                    unfocusedContainerColor = androidx.compose.ui.graphics.Color.White
                ),
                shape = RoundedCornerShape(Design.TEXTFIELD_RADIUS)
            )
            
            // Contenido del drawer
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = Design.PADDING_STANDARD),
                verticalArrangement = Arrangement.spacedBy(Design.PADDING_SMALL)
            ) {
                // Opciones de navegación
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") },
                    selected = false,
                    onClick = onNavigateToHome
                )
                
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.Apps, contentDescription = "Productos") },
                    label = { Text("Productos") },
                    selected = false,
                    onClick = onNavigateToProducts
                )
                
                NavigationDrawerItem(
                    icon = {
                        BadgedBox(badge = {
                            if (totalItems > 0) {
                                Badge { Text(totalItems.toString()) }
                            }
                        }) {
                            Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito")
                        }
                    },
                    label = { Text("Carrito") },
                    selected = false,
                    onClick = onNavigateToCart
                )
                
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Cuenta") },
                    label = { Text("Cuenta") },
                    selected = false,
                    onClick = onNavigateToAccount
                )
            }
            
            // Botones de autenticación
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Design.PADDING_STANDARD),
                verticalArrangement = Arrangement.spacedBy(Design.PADDING_SMALL)
            ) {
                if (isAuthenticated && user != null) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(Design.PADDING_STANDARD),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(Design.PADDING_STANDARD)
                        ) {
                            // Avatar con foto de perfil
                            ProfileImage(
                                user = user,
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                            )
                            
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(Design.PADDING_SMALL)
                            ) {
                                Text(
                                    text = user.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = user.email,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = onLogout,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Cerrar Sesión")
                    }
                } else {
                    Button(
                        onClick = onNavigateToLogin,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Iniciar Sesión")
                    }
                }
            }
        }
    }
}

