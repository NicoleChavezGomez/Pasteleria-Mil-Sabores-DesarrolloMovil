package com.example.milsaborestest.data.repository

import com.example.milsaborestest.data.mapper.toDomain
import com.example.milsaborestest.data.source.local.ProductJsonDataSource
import com.example.milsaborestest.domain.model.Category
import com.example.milsaborestest.domain.model.Product
import com.example.milsaborestest.domain.repository.ProductRepository
import javax.inject.Inject

/**
 * Implementación del repositorio de productos
 */
class ProductRepositoryImpl @Inject constructor(
    private val jsonDataSource: ProductJsonDataSource
) : ProductRepository {
    
    override suspend fun getCategories(): List<Category> {
        return jsonDataSource.getProductosData().getOrThrow().let { response ->
            response.categorias.map { (id, categoryDto) ->
                Category(
                    id = id,
                    nombre = categoryDto.nombre,
                    icono = categoryDto.icono,
                    productos = categoryDto.productos.map { it.toDomain() }
                )
            }
        }
    }
    
    override suspend fun getProductsByCategory(categoryId: String): List<Product> {
        return jsonDataSource.getProductosData().getOrThrow().let { response ->
            response.categorias[categoryId]?.productos?.map { it.toDomain() } ?: emptyList()
        }
    }
    
    override suspend fun getProductById(productId: String): Product? {
        return jsonDataSource.getProductosData().getOrThrow().let { response ->
            response.categorias.values.flatMap { it.productos }
                .firstOrNull { it.id == productId }
                ?.toDomain()
        }
    }
    
    override suspend fun getAllProducts(): List<Product> {
        return jsonDataSource.getProductosData().getOrThrow().let { response ->
            response.categorias.values.flatMap { it.productos.map { it.toDomain() } }
        }
    }
}

