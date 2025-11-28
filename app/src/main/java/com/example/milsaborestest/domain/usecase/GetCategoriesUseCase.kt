package com.example.milsaborestest.domain.usecase

import com.example.milsaborestest.domain.model.Category
import com.example.milsaborestest.domain.repository.ProductRepository

/**
 * Caso de uso para obtener todas las categorías
 */
class GetCategoriesUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<Category> = repository.getCategories()
}

