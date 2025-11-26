package com.example.milsaborestest.presentation.ui.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.milsaborestest.presentation.navigation.Screen
import com.example.milsaborestest.presentation.viewmodel.AuthViewModel
import com.example.milsaborestest.ui.theme.CardWhite
import com.example.milsaborestest.ui.theme.TextDark
import com.example.milsaborestest.util.Constants.Design

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var shakeError by remember { mutableStateOf(false) }
    
    val isAuthenticated by authViewModel.isAuthenticated.collectAsState()
    val message by authViewModel.message.collectAsState()
    
    // Animación de shake para errores
    val shakeOffset by animateFloatAsState(
        targetValue = if (shakeError) 0f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "shakeAnimation"
    )
    
    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated) {
            navController.navigate(Screen.Home.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }
    
    LaunchedEffect(message) {
        message?.let {
            if (!isAuthenticated) {
                emailError = it
                shakeError = true
                kotlinx.coroutines.delay(500)
                shakeError = false
            } else {
                emailError = ""
            }
            authViewModel.clearMessage()
        }
    }
    
    Column(modifier = Modifier.fillMaxSize()) {
        // TopBar
        Surface(
            color = CardWhite,
            shadowElevation = Design.CARD_ELEVATION
        ) {
            TopAppBar(
                title = { Text("Iniciar Sesión") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CardWhite,
                    titleContentColor = TextDark
                )
            )
        }
        
        // Contenido
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Design.PADDING_STANDARD)
                    .graphicsLayer {
                        // Efecto shake cuando hay error
                        translationX = if (shakeError) {
                            (shakeOffset * 10f) * kotlin.math.sin(shakeOffset * kotlin.math.PI.toFloat() * 8)
                        } else 0f
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = Design.LOGIN_CARD_ELEVATION),
                colors = CardDefaults.cardColors(containerColor = CardWhite)
            ) {
                Column(
                    modifier = Modifier.padding(Design.PADDING_EXTRA_LARGE),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(Design.PADDING_STANDARD)
                ) {
                    Text(
                        text = "Mil Sabores",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Text(
                        text = "Inicia sesión para continuar",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = ""
                        },
                        label = { Text("Email") },
                        leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email") },
                        isError = emailError.isNotEmpty(),
                        supportingText = if (emailError.isNotEmpty()) {
                            { Text(emailError) }
                        } else null,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = ""
                        },
                        label = { Text("Contraseña") },
                        leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Contraseña") },
                        trailingIcon = {
                            IconButton(onClick = { showPassword = !showPassword }) {
                                Icon(
                                    if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = if (showPassword) "Ocultar" else "Mostrar"
                                )
                            }
                        },
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        isError = passwordError.isNotEmpty(),
                        supportingText = if (passwordError.isNotEmpty()) {
                            { Text(passwordError) }
                        } else null,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    
                    Button(
                        onClick = {
                            if (email.isBlank() || password.isBlank()) {
                                emailError = if (email.isBlank()) "Campo requerido" else ""
                                passwordError = if (password.isBlank()) "Campo requerido" else ""
                                return@Button
                            }
                            
                            authViewModel.login(email, password)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Iniciar Sesión")
                    }
                    
                    TextButton(onClick = { navController.navigate(Screen.Register.route) }) {
                        Text("¿No tienes cuenta? Regístrate")
                    }
                }
            }
        }
    }
}

