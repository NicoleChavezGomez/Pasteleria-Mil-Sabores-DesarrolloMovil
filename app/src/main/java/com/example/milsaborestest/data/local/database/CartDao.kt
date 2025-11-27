package com.example.milsaborestest.data.local.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    
    @Query("SELECT * FROM cart_items WHERE userId = :userId")
    fun getAllCartItems(userId: Int): Flow<List<CartEntity>>
    
    @Query("SELECT * FROM cart_items WHERE id = :productId AND userId = :userId")
    suspend fun getCartItemById(productId: String, userId: Int): CartEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartEntity)
    
    @Update
    suspend fun updateCartItem(cartItem: CartEntity)
    
    @Delete
    suspend fun deleteCartItem(cartItem: CartEntity)
    
    @Query("DELETE FROM cart_items WHERE id = :productId AND userId = :userId")
    suspend fun deleteCartItemById(productId: String, userId: Int)
    
    @Query("DELETE FROM cart_items WHERE userId = :userId")
    suspend fun clearCart(userId: Int)
}

