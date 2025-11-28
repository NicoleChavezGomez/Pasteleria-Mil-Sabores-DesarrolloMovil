package com.example.milsaborestest.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categoria")
data class CategoryEntity(
    @PrimaryKey
    val id: String,
    val nombre: String,
    val icono: String
)

