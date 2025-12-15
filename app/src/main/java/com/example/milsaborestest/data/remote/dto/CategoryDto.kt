package com.example.milsaborestest.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para categorías recibidas de la API
 */
data class CategoryDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("icono")
    val icono: String
)

