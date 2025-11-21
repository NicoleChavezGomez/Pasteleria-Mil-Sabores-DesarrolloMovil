package com.example.milsaborestest.data.mapper

import com.example.milsaborestest.data.local.dto.ProductDto
import com.example.milsaborestest.data.local.dto.ReviewDto
import com.example.milsaborestest.domain.model.Product
import com.example.milsaborestest.domain.model.Review

/**
 * Mappers para convertir DTOs a modelos de dominio
 */

fun ReviewDto.toDomain(): Review {
    return Review(
        autor = autor,
        fecha = fecha,
        rating = rating,
        comentario = comentario
    )
}

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

