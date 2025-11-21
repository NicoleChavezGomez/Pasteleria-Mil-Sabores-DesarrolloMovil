package com.example.milsaborestest.data.local.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para Category desde JSON
 */
data class CategoryDto(
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("icono")
    val icono: String,
    @SerializedName("productos")
    val productos: List<ProductDto>
)

