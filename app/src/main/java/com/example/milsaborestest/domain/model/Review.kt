package com.example.milsaborestest.domain.model

/**
 * Modelo de dominio para una reseña de producto
 */
data class Review(
    val id: Int? = null,
    val autor: String,
    val fecha: String,
    val rating: Int,
    val comentario: String,
    val userId: Int? = null
)

