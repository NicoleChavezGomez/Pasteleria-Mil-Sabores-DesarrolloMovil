package com.example.milsaborestest.domain.model

/**
 * Modelo de dominio para una categoría de productos
 */
data class Category(
    val id: String,
    val nombre: String,
    val icono: String,
    val productos: List<Product>
)

