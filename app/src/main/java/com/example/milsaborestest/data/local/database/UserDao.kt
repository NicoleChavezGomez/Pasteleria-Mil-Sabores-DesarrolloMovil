package com.example.milsaborestest.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
    suspend fun insertar(usuario: UserEntity): Long

    @Update
    suspend fun actualizar(usuario: UserEntity)

    @Delete
    suspend fun eliminar(usuario: UserEntity)

    @Query("SELECT * FROM usuario")
    suspend fun obtenerTodos(): List<UserEntity>

    @Query("SELECT * FROM usuario WHERE id = :id")
    suspend fun obtenerPorId(id: Int): UserEntity?

    @Query("SELECT * FROM usuario WHERE email = :email AND contrasena = :contrasena")
    suspend fun login(email: String, contrasena: String): UserEntity?

    @Query("SELECT * FROM usuario WHERE email = :email")
    suspend fun obtenerPorEmail(email: String): UserEntity?
}

