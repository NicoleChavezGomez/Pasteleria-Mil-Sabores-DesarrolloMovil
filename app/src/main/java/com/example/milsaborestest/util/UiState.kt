package com.example.milsaborestest.util

/**
 * Estados de UI para las pantallas.
 * Representa los diferentes estados que puede tener una pantalla.
 */
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

