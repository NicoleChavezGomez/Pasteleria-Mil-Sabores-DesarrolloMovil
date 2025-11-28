package com.example.milsaborestest.domain.usecase

import com.example.milsaborestest.domain.model.Product
import com.example.milsaborestest.domain.repository.ProductRepository

/**
 * Caso de uso para obtener productos destacados (rating >= 4.8)
 */
class GetFeaturedProductsUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<Product> {
        return repository.getAllProducts().filter { it.rating >= 4.8 }
    }
}

