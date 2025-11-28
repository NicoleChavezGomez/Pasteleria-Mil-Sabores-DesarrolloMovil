package com.example.milsaborestest.data.repository

import com.example.milsaborestest.data.local.database.CategoryDao
import com.example.milsaborestest.data.local.database.ProductDao
import com.example.milsaborestest.data.mapper.toDomain
import com.example.milsaborestest.domain.model.Category
import com.example.milsaborestest.domain.model.Product
import com.example.milsaborestest.domain.repository.ProductRepository

/**
 * Implementación del repositorio de productos usando Room Database
 */
class ProductRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val productDao: ProductDao
) : ProductRepository {
    
    override suspend fun getCategories(): List<Category> {
        val categoryEntities = categoryDao.obtenerTodasSuspend()
        return categoryEntities.map { categoryEntity ->
            // Cargar productos para cada categoría
            val productos = getProductsByCategory(categoryEntity.id)
            Category(
                id = categoryEntity.id,
                nombre = categoryEntity.nombre,
                icono = categoryEntity.icono,
                productos = productos
            )
        }
    }
    
    override suspend fun getProductsByCategory(categoryId: String): List<Product> {
        val productEntities = productDao.obtenerPorCategoriaSuspend(categoryId)
        return productEntities.map { it.toDomain() }
    }
    
    override suspend fun getProductById(productId: String): Product? {
        val productEntity = productDao.obtenerPorId(productId) ?: return null
        return productEntity.toDomain()
    }
    
    override suspend fun getAllProducts(): List<Product> {
        val productEntities = productDao.obtenerTodosSuspend()
        return productEntities.map { it.toDomain() }
    }
}

