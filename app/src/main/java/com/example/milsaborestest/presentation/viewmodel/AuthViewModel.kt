package com.example.milsaborestest.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaborestest.data.local.database.AppDatabase
import com.example.milsaborestest.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    
    private val database = AppDatabase.getDatabase(application)
    private val userDao = database.userDao()
    
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()
    
    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()
    
    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                // Validación básica
                if (email.isBlank() || password.isBlank()) {
                    _message.value = "Por favor completa todos los campos"
                    return@launch
                }
                
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    _message.value = "Email inválido"
                    return@launch
                }
                
                if (password.length < 6) {
                    _message.value = "La contraseña debe tener al menos 6 caracteres"
                    return@launch
                }
                
                // Simular delay de red
                delay(1000)
                
                // Buscar usuario en la base de datos
                val usuarioEncontrado = userDao.login(email, password)
                
                if (usuarioEncontrado != null) {
                    // Convertir UserEntity a User del dominio
                    val loggedUser = User(
                        id = usuarioEncontrado.id.toString(),
                        email = usuarioEncontrado.email,
                        name = usuarioEncontrado.nombre,
                        loginDate = System.currentTimeMillis().toString(),
                        isAuthenticated = true,
                        fotoPerfil = usuarioEncontrado.fotoPerfil
                    )
                    
                    _user.value = loggedUser
                    _isAuthenticated.value = true
                    _message.value = "Login exitoso"
                } else {
                    _message.value = "Email o contraseña incorrectos"
                    _isAuthenticated.value = false
                    _user.value = null
                }
            } catch (e: Exception) {
                _message.value = "Error al iniciar sesión: ${e.message}"
                _isAuthenticated.value = false
                _user.value = null
            }
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            try {
                _user.value = null
                _isAuthenticated.value = false
                _message.value = "Sesión cerrada"
            } catch (e: Exception) {
                _message.value = "Error al cerrar sesión: ${e.message}"
            }
        }
    }
    
    fun register(nombre: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                // Validación básica
                if (nombre.isBlank() || email.isBlank() || password.isBlank()) {
                    _message.value = "Por favor completa todos los campos"
                    return@launch
                }
                
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    _message.value = "Email inválido"
                    return@launch
                }
                
                if (password.length < 6) {
                    _message.value = "La contraseña debe tener al menos 6 caracteres"
                    return@launch
                }
                
                // Verificar si el email ya existe
                val usuarioExistente = userDao.obtenerPorEmail(email)
                if (usuarioExistente != null) {
                    _message.value = "Este email ya está registrado"
                    return@launch
                }
                
                // Simular delay de red
                delay(1000)
                
                // Crear nuevo usuario
                val nuevoUsuario = com.example.milsaborestest.data.local.database.UserEntity(
                    nombre = nombre,
                    email = email,
                    contrasena = password
                )
                
                val userId = userDao.insertar(nuevoUsuario)
                
                // Obtener el usuario recién insertado para tener el ID correcto
                val usuarioInsertado = userDao.obtenerPorId(userId.toInt())
                
                if (usuarioInsertado != null) {
                    // Convertir UserEntity a User del dominio y autenticar automáticamente
                    val loggedUser = User(
                        id = usuarioInsertado.id.toString(),
                        email = usuarioInsertado.email,
                        name = usuarioInsertado.nombre,
                        loginDate = System.currentTimeMillis().toString(),
                        isAuthenticated = true,
                        fotoPerfil = usuarioInsertado.fotoPerfil
                    )
                    
                    _user.value = loggedUser
                    _isAuthenticated.value = true
                    _message.value = "Registro exitoso"
                } else {
                    _message.value = "Error al obtener usuario recién creado"
                    _isAuthenticated.value = false
                    _user.value = null
                }
            } catch (e: Exception) {
                _message.value = "Error al registrar: ${e.message}"
                _isAuthenticated.value = false
                _user.value = null
            }
        }
    }
    
    fun clearMessage() {
        _message.value = null
    }
}

