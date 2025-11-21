package com.example.milsaborestest.domain.repository

import com.example.milsaborestest.domain.model.Category
import com.example.milsaborestest.domain.model.Product

/**
 * Interface del repositorio de productos
 * Define el contrato para obtener datos de productos
 */
interface ProductRepository {
    suspend fun getCategories(): List<Category>
    suspend fun getProductsByCategory(categoryId: String): List<Product>
    suspend fun getProductById(productId: String): Product?
    suspend fun getAllProducts(): List<Product>
}

