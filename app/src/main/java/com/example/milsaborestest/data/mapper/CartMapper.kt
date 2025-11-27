package com.example.milsaborestest.data.mapper

import com.example.milsaborestest.data.local.database.CartEntity

/**
 * Mapper para convertir CartEntity a modelos
 */

// Función de extensión para convertir CartEntity a CartItem
fun CartEntity.toCartItem(): com.example.milsaborestest.domain.model.CartItem {
    return com.example.milsaborestest.domain.model.CartItem(
        id = id,
        nombre = nombre,
        precio = precio,
        cantidad = cantidad,
        imagen = imagen,
        descripcion = descripcion
    )
}

// Función de extensión para convertir CartItem a CartEntity
fun com.example.milsaborestest.domain.model.CartItem.toCartEntity(userId: Int): CartEntity {
    return CartEntity(
        id = id,
        userId = userId,
        nombre = nombre,
        precio = precio,
        cantidad = cantidad,
        imagen = imagen,
        descripcion = descripcion
    )
}

