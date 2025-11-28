package com.example.milsaborestest.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    
    @Query("SELECT * FROM categoria ORDER BY nombre")
    fun obtenerTodas(): Flow<List<CategoryEntity>>
    
    @Query("SELECT * FROM categoria WHERE id = :categoryId")
    suspend fun obtenerPorId(categoryId: String): CategoryEntity?
    
    @Query("SELECT * FROM categoria ORDER BY nombre")
    suspend fun obtenerTodasSuspend(): List<CategoryEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(categoria: CategoryEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodas(categorias: List<CategoryEntity>)
    
    @Query("SELECT COUNT(*) FROM categoria")
    suspend fun contar(): Int
}

