package com.example.milsaborestest.presentation.ui.screens.account

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.milsaborestest.R
import com.example.milsaborestest.domain.model.User
import com.example.milsaborestest.presentation.navigation.Screen
import com.example.milsaborestest.presentation.viewmodel.AuthViewModel
import com.example.milsaborestest.ui.theme.CardWhite
import com.example.milsaborestest.ui.theme.TextDark
import com.example.milsaborestest.util.Constants.Design
import com.example.milsaborestest.util.ImageHelper
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val isAuthenticated by authViewModel.isAuthenticated.collectAsState()
    val user by authViewModel.user.collectAsState()
    val context = LocalContext.current
    
    // Launcher para seleccionar imagen de galería
    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null && user != null) {
            // Convertir URI a Bitmap
            val bitmap = ImageHelper.uriToBitmap(context, uri)
            if (bitmap != null) {
                val userId = user!!.id.toIntOrNull()
                if (userId != null) {
                    // Eliminar imagen antigua si existe
                    user?.fotoPerfil?.let { oldPath ->
                        ImageHelper.deleteProfileImage(context, oldPath)
                    }
                    
                    // Guardar nueva imagen
                    val imagePath = ImageHelper.saveProfileImage(context, bitmap, userId)
                    if (imagePath != null) {
                        // Actualizar en ViewModel y base de datos
                        authViewModel.updateProfilePhoto(imagePath)
                    }
                }
            }
        }
    }
    
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
                    // Avatar con foto de perfil o imagen por defecto
                    Box(
                        modifier = Modifier.size(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        ProfileImage(
                            user = user!!,
                            context = context,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                        )
                        
                        // Botón flotante para editar foto
                        FloatingActionButton(
                            onClick = {
                                pickMedia.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(32.dp),
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Editar foto",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    
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
                        onClick = { navController.navigate(Screen.Login.route) },
                        modifier = Modifier.padding(top = Design.PADDING_SMALL)
                    ) {
                        Text("Entrar")
                    }
                }
            }
            
            HorizontalDivider()
            
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
                
                HorizontalDivider()
                
                MenuOptionItem(
                    icon = Icons.AutoMirrored.Filled.ExitToApp,
                    title = "Cerrar Sesión",
                    onClick = { 
                        authViewModel.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

/**
 * Componente para mostrar la foto de perfil con manejo de errores
 */
@Composable
fun ProfileImage(
    user: User,
    context: Context,
    modifier: Modifier = Modifier
) {
    val imagePath = user.fotoPerfil
    
    if (imagePath != null && imagePath.isNotBlank()) {
        // Intentar cargar imagen desde storage
        val imageFile = File(imagePath)
        if (imageFile.exists()) {
            AsyncImage(
                model = imageFile,
                contentDescription = "Foto de perfil",
                placeholder = painterResource(R.drawable.ic_profile_default),
                error = painterResource(R.drawable.ic_profile_default),
                fallback = painterResource(R.drawable.ic_profile_default),
                modifier = modifier
            )
        } else {
            // Archivo no existe, mostrar imagen por defecto
            Image(
                painter = painterResource(R.drawable.ic_profile_default),
                contentDescription = "Foto de perfil por defecto",
                modifier = modifier
            )
        }
    } else {
        // No hay foto, mostrar imagen por defecto
        Image(
            painter = painterResource(R.drawable.ic_profile_default),
            contentDescription = "Foto de perfil por defecto",
            modifier = modifier
        )
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

