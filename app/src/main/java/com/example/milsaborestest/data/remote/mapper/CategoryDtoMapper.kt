package com.example.milsaborestest.data.remote.mapper

import com.example.milsaborestest.data.remote.dto.CategoryDto
import com.example.milsaborestest.domain.model.Category

/**
 * Mapper para convertir CategoryDto a Category (Domain Model)
 */
fun CategoryDto.toDomain(): Category {
    return Category(
        id = this.id,
        nombre = this.nombre,
        icono = this.icono,
        productos = emptyList() // Los productos se cargan por separado
    )
}

