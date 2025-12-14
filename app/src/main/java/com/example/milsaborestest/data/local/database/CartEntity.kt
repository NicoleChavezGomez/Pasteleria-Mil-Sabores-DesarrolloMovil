package com.example.milsaborestest.data.local.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cart_items",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"])]
)
data class CartEntity(
    @PrimaryKey
    val id: String,
    val userId: Int,
    val nombre: String,
    val precio: Int,
    val cantidad: Int,
    val imagen: String,
    val descripcion: String
)

