package com.example.milsaborestest.presentation.ui.screens.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.milsaborestest.R
import com.example.milsaborestest.presentation.viewmodel.AuthViewModel
import com.example.milsaborestest.ui.theme.CardWhite
import com.example.milsaborestest.ui.theme.TextDark
import com.example.milsaborestest.util.Constants.Design

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    onNavigateToLogin: () -> Unit,
    authViewModel: AuthViewModel
) {
    val isAuthenticated by authViewModel.isAuthenticated.collectAsState()
    val user by authViewModel.user.collectAsState()
    
    Column(modifier = Modifier.fillMaxSize()) {
        // TopBar
        Surface(
            color = CardWhite,
            shadowElevation = Design.CARD_ELEVATION
        ) {
            TopAppBar(
                title = { Text("Mi Cuenta") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CardWhite,
                    titleContentColor = TextDark
                )
            )
        }
        
        // Contenido
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(Design.PADDING_EXTRA_LARGE),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Design.SPACING_LARGE)
        ) {
            // Avatar o Login
            if (isAuthenticated && user != null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(Design.PADDING_MEDIUM)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_milsabores),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
                    
                    Text(
                        text = user!!.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = user!!.email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(Design.PADDING_MEDIUM)
                ) {
                    Icon(
                        Icons.Filled.AccountCircle,
                        contentDescription = "Usuario",
                        modifier = Modifier.size(100.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    
                    Text(
                        text = "Inicia Sesión",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Button(
                        onClick = onNavigateToLogin,
                        modifier = Modifier.padding(top = Design.PADDING_SMALL)
                    ) {
                        Text("Entrar")
                    }
                }
            }
            
            Divider()
            
            // Opciones de menú
            if (isAuthenticated && user != null) {
                MenuOptionItem(
                    icon = Icons.Filled.ShoppingBag,
                    title = "Mis Pedidos",
                    onClick = { /* TODO: Implementar historial */ }
                )
                
                MenuOptionItem(
                    icon = Icons.Filled.Favorite,
                    title = "Favoritos",
                    onClick = { /* TODO: Implementar favoritos */ }
                )
                
                MenuOptionItem(
                    icon = Icons.Filled.LocationOn,
                    title = "Direcciones",
                    onClick = { /* TODO: Implementar direcciones */ }
                )
                
                MenuOptionItem(
                    icon = Icons.Filled.CreditCard,
                    title = "Métodos de Pago",
                    onClick = { /* TODO: Implementar pagos */ }
                )
                
                Divider()
                
                MenuOptionItem(
                    icon = Icons.Filled.ExitToApp,
                    title = "Cerrar Sesión",
                    onClick = { 
                        authViewModel.logout()
                        onNavigateToLogin()
                    }
                )
            }
        }
    }
}

@Composable
fun MenuOptionItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Design.PADDING_STANDARD),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Design.PADDING_STANDARD)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            Icon(
                Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

