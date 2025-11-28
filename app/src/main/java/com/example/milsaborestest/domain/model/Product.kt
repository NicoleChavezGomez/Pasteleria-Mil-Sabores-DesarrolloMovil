package com.example.milsaborestest.domain.model

/**
 * Modelo de dominio para un producto
 */
data class Product(
    val id: String,
    val nombre: String,
    val precio: Int,
    val imagen: String,
    val descripcion: String,
    val descripcionDetallada: String,
    val rating: Double,
    val reviews: Int,
    val porciones: String,
    val calorias: String,
    val ingredientes: String
)

