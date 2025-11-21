package com.example.milsaborestest.data.source.local

import android.content.Context
import com.example.milsaborestest.data.local.dto.ProductosResponseDto
import com.example.milsaborestest.util.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

/**
 * Data source para leer datos desde JSON local
 */
class ProductJsonDataSource(private val context: Context) {
    
    private val gson = Gson()
    private var cachedData: ProductosResponseDto? = null
    
    fun getProductosData(): Result<ProductosResponseDto> {
        return try {
            if (cachedData == null) {
                cachedData = loadFromAssets()
            }
            Result.success(cachedData!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun loadFromAssets(): ProductosResponseDto {
        return try {
            val jsonString = context.assets.open(Constants.PRODUCTOS_JSON_FILE)
                .bufferedReader()
                .use { it.readText() }
            
            gson.fromJson(jsonString, ProductosResponseDto::class.java)
        } catch (ioException: IOException) {
            throw Exception("Error al leer el archivo JSON", ioException)
        }
    }
}

