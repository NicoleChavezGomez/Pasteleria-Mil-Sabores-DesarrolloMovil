package com.example.milsaborestest.domain.model

/**
 * Modelo de dominio para una reseña de producto
 */
data class Review(
    val autor: String,
    val fecha: String,
    val rating: Int,
    val comentario: String
)

