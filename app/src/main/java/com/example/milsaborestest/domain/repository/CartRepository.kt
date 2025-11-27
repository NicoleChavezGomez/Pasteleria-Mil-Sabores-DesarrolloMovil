package com.example.milsaborestest.domain.repository

import com.example.milsaborestest.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

/**
 * Interface del repositorio del carrito
 */
interface CartRepository {
    fun getAllCartItems(userId: Int): Flow<List<CartItem>>
    suspend fun getCartItemById(productId: String, userId: Int): CartItem?
    suspend fun addToCart(cartItem: CartItem, userId: Int)
    suspend fun updateCartItem(cartItem: CartItem, userId: Int)
    suspend fun removeFromCart(productId: String, userId: Int)
    suspend fun clearCart(userId: Int)
    fun getCartItemCount(userId: Int): Flow<Int>
    fun getCartTotalPrice(userId: Int): Flow<Int>
}

