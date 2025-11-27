package com.example.milsaborestest.data.local.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Entidad de Room para representar un item individual dentro de una compra.
 * 
 * Almacena un snapshot del producto al momento de la compra (nombre, precio, imagen)
 * para mantener un historial preciso incluso si el producto cambia o se elimina.
 * 
 * @property id ID único del item (autogenerado)
 * @property purchaseId ID de la compra a la que pertenece este item (Foreign Key a PurchaseEntity)
 * @property productId ID del producto original (referencia, no FK porque productos pueden cambiar)
 * @property nombre Nombre del producto al momento de la compra
 * @property precio Precio unitario del producto al momento de la compra
 * @property cantidad Cantidad de unidades compradas
 * @property imagen URL de la imagen del producto
 */
@Entity(
    tableName = "purchase_items",
    foreignKeys = [
        ForeignKey(
            entity = PurchaseEntity::class,
            parentColumns = ["id"],
            childColumns = ["purchaseId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PurchaseItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val purchaseId: String,
    val productId: String,
    val nombre: String,
    val precio: Int,
    val cantidad: Int,
    val imagen: String
)
