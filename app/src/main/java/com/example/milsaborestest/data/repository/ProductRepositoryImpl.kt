package com.example.milsaborestest.data.repository

import android.content.Context
import com.example.milsaborestest.data.local.database.CategoryDao
import com.example.milsaborestest.data.local.database.ProductDao
import com.example.milsaborestest.data.mapper.toDomain
import com.example.milsaborestest.data.source.local.ProductJsonDataSource
import com.example.milsaborestest.domain.model.Category
import com.example.milsaborestest.domain.model.Product
import com.example.milsaborestest.domain.model.Review
import com.example.milsaborestest.domain.repository.ProductRepository

/**
 * Implementación del repositorio de productos usando Room Database
 */
class ProductRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val productDao: ProductDao,
    private val context: Context? = null  // Opcional, solo para cargar reseñas desde JSON
) : ProductRepository {
    
    // Cache para reseñas desde JSON (se cargan una vez)
    private var reseñasCache: Map<String, List<Review>>? = null
    
    private suspend fun getReseñasFromJson(): Map<String, List<Review>> {
        if (reseñasCache != null) {
            return reseñasCache!!
        }
        
        if (context == null) {
            return emptyMap()
        }
        
        return try {
            val jsonDataSource = ProductJsonDataSource(context)
            val productosData = jsonDataSource.getProductosData().getOrNull()
            if (productosData != null) {
                val map = mutableMapOf<String, List<Review>>()
                productosData.categorias.values.forEach { categoryDto ->
                    categoryDto.productos.forEach { productDto ->
                        map[productDto.id] = productDto.reseñas.map { it.toDomain() }
                    }
                }
                reseñasCache = map
                map
            } else {
                emptyMap()
            }
        } catch (e: Exception) {
            emptyMap()
        }
    }
    
    override suspend fun getCategories(): List<Category> {
        val categoryEntities = categoryDao.obtenerTodasSuspend()
        return categoryEntities.map { it.toDomain() }
    }
    
    override suspend fun getProductsByCategory(categoryId: String): List<Product> {
        val productEntities = productDao.obtenerPorCategoriaSuspend(categoryId)
        val reseñasMap = getReseñasFromJson()
        return productEntities.map { entity ->
            entity.toDomain(reseñasMap[entity.id] ?: emptyList())
        }
    }
    
    override suspend fun getProductById(productId: String): Product? {
        val productEntity = productDao.obtenerPorId(productId) ?: return null
        val reseñasMap = getReseñasFromJson()
        return productEntity.toDomain(reseñasMap[productId] ?: emptyList())
    }
    
    override suspend fun getAllProducts(): List<Product> {
        val productEntities = productDao.obtenerTodosSuspend()
        val reseñasMap = getReseñasFromJson()
        return productEntities.map { entity ->
            entity.toDomain(reseñasMap[entity.id] ?: emptyList())
        }
    }
}

