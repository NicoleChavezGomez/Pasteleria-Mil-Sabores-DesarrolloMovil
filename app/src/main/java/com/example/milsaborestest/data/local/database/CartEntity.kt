package com.example.milsaborestest.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartEntity(
    @PrimaryKey
    val id: String,
    val nombre: String,
    val precio: Int,
    val cantidad: Int,
    val imagen: String,
    val descripcion: String
)

