package com.example.milsaborestest.domain.repository

import com.example.milsaborestest.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

/**
 * Interface del repositorio del carrito
 */
interface CartRepository {
    fun getAllCartItems(): Flow<List<CartItem>>
    suspend fun getCartItemById(productId: String): CartItem?
    suspend fun addToCart(cartItem: CartItem)
    suspend fun updateCartItem(cartItem: CartItem)
    suspend fun removeFromCart(productId: String)
    suspend fun clearCart()
    fun getCartItemCount(): Flow<Int>
    fun getCartTotalPrice(): Flow<Int>
}

