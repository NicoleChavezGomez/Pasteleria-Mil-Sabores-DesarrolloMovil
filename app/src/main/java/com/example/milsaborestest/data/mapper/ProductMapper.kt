package com.example.milsaborestest.data.mapper

import com.example.milsaborestest.data.local.database.ProductEntity
import com.example.milsaborestest.domain.model.Product

/**
 * Mappers para convertir entre Entity y Domain para Product
 */
fun ProductEntity.toDomain(): Product {
    return Product(
        id = id,
        nombre = nombre,
        precio = precio,
        imagen = imagen,
        descripcion = descripcion,
        descripcionDetallada = descripcionDetallada,
        rating = rating,
        reviews = reviews,
        porciones = porciones,
        calorias = calorias,
        ingredientes = ingredientes
    )
}

fun List<ProductEntity>.toDomain(): List<Product> {
    return map { entity ->
        entity.toDomain()
    }
}

