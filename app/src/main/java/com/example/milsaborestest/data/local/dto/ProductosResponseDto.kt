package com.example.milsaborestest.data.local.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO raíz para la respuesta del JSON
 */
data class ProductosResponseDto(
    @SerializedName("categorias")
    val categorias: Map<String, CategoryDto>
)

