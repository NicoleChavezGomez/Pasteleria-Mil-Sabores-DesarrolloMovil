package com.example.milsaborestest.data.repository

import com.example.milsaborestest.data.local.database.CartDao
import com.example.milsaborestest.data.mapper.toCartEntity
import com.example.milsaborestest.data.mapper.toCartItem
import com.example.milsaborestest.domain.model.CartItem
import com.example.milsaborestest.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {
    
    override fun getAllCartItems(): Flow<List<CartItem>> {
        return cartDao.getAllCartItems().map { entities ->
            entities.map { it.toCartItem() }
        }
    }
    
    override suspend fun getCartItemById(productId: String): CartItem? {
        return cartDao.getCartItemById(productId)?.toCartItem()
    }
    
    override suspend fun addToCart(cartItem: CartItem) {
        val existingItem = cartDao.getCartItemById(cartItem.id)
        if (existingItem != null) {
            // Si ya existe, actualizar la cantidad
            val updatedItem = existingItem.copy(cantidad = existingItem.cantidad + cartItem.cantidad)
            cartDao.updateCartItem(updatedItem)
        } else {
            // Si no existe, insertar nuevo
            cartDao.insertCartItem(cartItem.toCartEntity())
        }
    }
    
    override suspend fun updateCartItem(cartItem: CartItem) {
        cartDao.updateCartItem(cartItem.toCartEntity())
    }
    
    override suspend fun removeFromCart(productId: String) {
        cartDao.deleteCartItemById(productId)
    }
    
    override suspend fun clearCart() {
        cartDao.clearCart()
    }
    
    override fun getCartItemCount(): Flow<Int> {
        return getAllCartItems().map { list ->
            list.sumOf { it.cantidad }
        }
    }
    
    override fun getCartTotalPrice(): Flow<Int> {
        return getAllCartItems().map { list ->
            list.sumOf { it.subtotal }
        }
    }
}

