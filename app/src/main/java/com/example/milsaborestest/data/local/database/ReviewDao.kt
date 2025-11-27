package com.example.milsaborestest.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ReviewDao {

    @Insert
    suspend fun insertar(review: ReviewEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarTodas(reviews: List<ReviewEntity>)

    @Query("SELECT * FROM reseñas WHERE productId = :productId ORDER BY fecha DESC, id DESC")
    suspend fun obtenerPorProducto(productId: String): List<ReviewEntity>

    @Query("SELECT * FROM reseñas WHERE userId = :userId")
    suspend fun obtenerPorUsuario(userId: Int): List<ReviewEntity>

    @Query("SELECT * FROM reseñas WHERE productId = :productId AND userId = :userId LIMIT 1")
    suspend fun obtenerPorProductoYUsuario(productId: String, userId: Int): ReviewEntity?

    @Query("SELECT AVG(rating) FROM reseñas WHERE productId = :productId")
    suspend fun obtenerRatingPromedio(productId: String): Double?

    @Query("SELECT COUNT(*) FROM reseñas WHERE productId = :productId")
    suspend fun obtenerCantidadReseñas(productId: String): Int

    @Query("SELECT COUNT(*) FROM reseñas")
    suspend fun contarReseñas(): Int
}


