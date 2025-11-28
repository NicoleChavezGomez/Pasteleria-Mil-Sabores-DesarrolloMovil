package com.example.milsaborestest.data.local.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "producto",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["categoryId"])]
)
data class ProductEntity(
    @PrimaryKey
    val id: String,
    val nombre: String,
    val precio: Int,
    val imagen: String,
    val descripcion: String,
    val descripcionDetallada: String,
    val rating: Double,
    val reviews: Int,
    val porciones: String,
    val calorias: String,
    val ingredientes: String,
    val categoryId: String  // Foreign Key a CategoryEntity
)

