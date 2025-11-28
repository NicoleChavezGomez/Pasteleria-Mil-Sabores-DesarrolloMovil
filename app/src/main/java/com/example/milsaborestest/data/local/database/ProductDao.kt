package com.example.milsaborestest.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    
    @Query("SELECT * FROM producto WHERE id = :productId")
    suspend fun obtenerPorId(productId: String): ProductEntity?
    
    @Query("SELECT * FROM producto WHERE categoryId = :categoryId ORDER BY nombre")
    suspend fun obtenerPorCategoriaSuspend(categoryId: String): List<ProductEntity>
    
    @Query("SELECT * FROM producto ORDER BY nombre")
    suspend fun obtenerTodosSuspend(): List<ProductEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(producto: ProductEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(productos: List<ProductEntity>)
}

