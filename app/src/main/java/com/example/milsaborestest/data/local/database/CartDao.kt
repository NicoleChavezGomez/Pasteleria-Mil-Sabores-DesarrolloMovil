package com.example.milsaborestest.data.local.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    
    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartEntity>>
    
    @Query("SELECT * FROM cart_items WHERE id = :productId")
    suspend fun getCartItemById(productId: String): CartEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartEntity)
    
    @Update
    suspend fun updateCartItem(cartItem: CartEntity)
    
    @Delete
    suspend fun deleteCartItem(cartItem: CartEntity)
    
    @Query("DELETE FROM cart_items WHERE id = :productId")
    suspend fun deleteCartItemById(productId: String)
    
    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}

