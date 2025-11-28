package com.example.milsaborestest.data.mapper

import com.example.milsaborestest.data.local.database.CategoryEntity
import com.example.milsaborestest.domain.model.Category

/**
 * Mappers para convertir entre Entity y Domain para Category
 */

fun CategoryEntity.toDomain(): Category {
    return Category(
        id = id,
        nombre = nombre,
        icono = icono,
        productos = emptyList() // Los productos se cargan por separado
    )
}

fun List<CategoryEntity>.toDomain(): List<Category> {
    return map { it.toDomain() }
}

