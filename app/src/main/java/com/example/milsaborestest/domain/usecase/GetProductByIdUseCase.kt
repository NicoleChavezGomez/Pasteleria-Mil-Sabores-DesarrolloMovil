package com.example.milsaborestest.domain.usecase

import com.example.milsaborestest.domain.model.Product
import com.example.milsaborestest.domain.repository.ProductRepository
import javax.inject.Inject

/**
 * Caso de uso para obtener un producto por ID
 */
class GetProductByIdUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productId: String): Product? = 
        repository.getProductById(productId)
}

