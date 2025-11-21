package com.example.milsaborestest.domain.usecase

import com.example.milsaborestest.domain.model.Product
import com.example.milsaborestest.domain.repository.ProductRepository
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<Product> {
        return repository.getAllProducts()
    }
}

