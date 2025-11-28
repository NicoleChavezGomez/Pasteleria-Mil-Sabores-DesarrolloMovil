package com.example.milsaborestest.domain.usecase

import com.example.milsaborestest.domain.model.Product
import com.example.milsaborestest.domain.repository.ProductRepository

/**
 * Caso de uso para obtener productos por categoría
 */
class GetProductsByCategoryUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(categoryId: String): List<Product> = 
        repository.getProductsByCategory(categoryId)
}

