package com.example.milsaborestest.data.remote.mapper

import com.example.milsaborestest.data.remote.dto.ProductDto
import com.example.milsaborestest.domain.model.Product

/**
 * Mapper para convertir ProductDto a Product (Domain Model)
 */
fun ProductDto.toDomain(): Product {
    return Product(
        id = this.id,
        nombre = this.nombre,
        precio = this.precio,
        imagen = this.imagen,
        descripcion = this.descripcion,
        descripcionDetallada = this.descripcionDetallada,
        rating = this.rating,
        reviews = this.reviews,
        porciones = this.porciones,
        calorias = this.calorias,
        ingredientes = this.ingredientes
    )
}

