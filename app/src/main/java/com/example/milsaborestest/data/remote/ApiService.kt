package com.example.milsaborestest.data.remote

import com.example.milsaborestest.data.remote.dto.CategoryDto
import com.example.milsaborestest.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("categories")
    suspend fun getCategories(): List<CategoryDto>
    
    @GET("products")
    suspend fun getProducts(): List<ProductDto>
    
    @GET("products")
    suspend fun getProductsByCategory(
        @Query("categoryId") categoryId: String
    ): List<ProductDto>
    
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: String): ProductDto
}

