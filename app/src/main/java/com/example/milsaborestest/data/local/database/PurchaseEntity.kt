package com.example.milsaborestest.data.local.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Entidad de Room para representar una compra/pedido en la base de datos.
 * 
 * Almacena información general de la compra realizada por un usuario.
 * Los items individuales de la compra se almacenan en PurchaseItemEntity.
 * 
 * @property id ID único de la compra (UUID generado al momento de crear)
 * @property userId ID del usuario que realizó la compra (Foreign Key a UserEntity)
 * @property fecha Fecha y hora de la compra en formato ISO 8601 (ej: "2025-11-26T23:30:00")
 * @property total Total de la compra en pesos chilenos
 * @property estado Estado de la compra (ej: "completada", "pendiente", "cancelada")
 */
@Entity(
    tableName = "compras",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PurchaseEntity(
    @PrimaryKey
    val id: String,
    val userId: Int,
    val fecha: String,
    val total: Int,
    val estado: String = "completada"
)
