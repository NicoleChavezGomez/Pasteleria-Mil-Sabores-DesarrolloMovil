package com.example.milsaborestest.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaborestest.data.local.AuthDataStoreManager
import com.example.milsaborestest.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    application: Application,
    private val authDataStore: AuthDataStoreManager
) : AndroidViewModel(application) {
    
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()
    
    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()
    
    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()
    
    init {
        loadUser()
    }
    
    private fun loadUser() {
        viewModelScope.launch {
            authDataStore.getUser().collect { user ->
                _user.value = user
                _isAuthenticated.value = user != null
            }
        }
    }
    
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
                
                // Simular login exitoso
                kotlinx.coroutines.delay(1000)
                
                val loggedUser = User(
                    id = "1",
                    email = email,
                    name = "Usuario Demo",
                    loginDate = System.currentTimeMillis().toString(),
                    isAuthenticated = true
                )
                
                authDataStore.saveUser(loggedUser)
                _message.value = "Login exitoso"
            } catch (e: Exception) {
                _message.value = "Error al iniciar sesión: ${e.message}"
            }
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            try {
                authDataStore.clearUser()
                _message.value = "Sesión cerrada"
            } catch (e: Exception) {
                _message.value = "Error al cerrar sesión: ${e.message}"
            }
        }
    }
    
    fun clearMessage() {
        _message.value = null
    }
}

