package com.example.milsaborestest.domain.model

/**
 * Modelo de dominio para un item del carrito
 */
data class CartItem(
    val id: String,
    val nombre: String,
    val precio: Int,
    val cantidad: Int,
    val imagen: String,
    val descripcion: String
) {
    val subtotal: Int
        get() = precio * cantidad
}

