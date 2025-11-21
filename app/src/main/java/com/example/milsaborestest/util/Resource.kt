package com.example.milsaborestest.util

/**
 * Clase genérica para representar el resultado de operaciones.
 * Útil para repositorios y casos de uso.
 */
sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Throwable) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

