package com.example.milsaborestest.data.mapper

import com.example.milsaborestest.data.local.dto.ProductDto
import com.example.milsaborestest.domain.model.Product

/**
 * Mappers para convertir DTOs a modelos de dominio
 */
fun ProductDto.toDomain(): Product {
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
        ingredientes = ingredientes,
        reseñas = reseñas.map { it.toDomain() }
    )
}

