package com.example.milsaborestest.data.repository

import com.example.milsaborestest.data.local.database.CartDao
import com.example.milsaborestest.data.mapper.toCartEntity
import com.example.milsaborestest.data.mapper.toCartItem
import com.example.milsaborestest.domain.model.CartItem
import com.example.milsaborestest.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartRepositoryImpl(
    private val cartDao: CartDao
) : CartRepository {
    
    override fun getAllCartItems(userId: Int): Flow<List<CartItem>> {
        return cartDao.getAllCartItems(userId).map { entities ->
            entities.map { it.toCartItem() }
        }
    }
    
    override suspend fun getCartItemById(productId: String, userId: Int): CartItem? {
        return cartDao.getCartItemById(productId, userId)?.toCartItem()
    }
    
    override suspend fun addToCart(cartItem: CartItem, userId: Int) {
        val existingItem = cartDao.getCartItemById(cartItem.id, userId)
        if (existingItem != null) {
            // Si ya existe, actualizar la cantidad
            val updatedItem = existingItem.copy(cantidad = existingItem.cantidad + cartItem.cantidad)
            cartDao.updateCartItem(updatedItem)
        } else {
            // Si no existe, insertar nuevo
            cartDao.insertCartItem(cartItem.toCartEntity(userId))
        }
    }
    
    override suspend fun updateCartItem(cartItem: CartItem, userId: Int) {
        cartDao.updateCartItem(cartItem.toCartEntity(userId))
    }
    
    override suspend fun removeFromCart(productId: String, userId: Int) {
        cartDao.deleteCartItemById(productId, userId)
    }
    
    override suspend fun clearCart(userId: Int) {
        cartDao.clearCart(userId)
    }
    
    override fun getCartItemCount(userId: Int): Flow<Int> {
        return getAllCartItems(userId).map { list ->
            list.sumOf { it.cantidad }
        }
    }
    
    override fun getCartTotalPrice(userId: Int): Flow<Int> {
        return getAllCartItems(userId).map { list ->
            list.sumOf { it.subtotal }
        }
    }
}

