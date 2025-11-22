package com.example.milsaborestest.domain.model

/**
 * Modelo de dominio para un usuario
 */
data class User(
    val id: String,
    val email: String,
    val name: String,
    val loginDate: String? = null,
    val isAuthenticated: Boolean = true,
    val fotoPerfil: String? = null  // Ruta del archivo de imagen de perfil
)

