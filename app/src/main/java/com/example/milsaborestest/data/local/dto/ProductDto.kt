package com.example.milsaborestest.data.local.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para Product desde JSON
 */
data class ProductDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("precio")
    val precio: Int,
    @SerializedName("imagen")
    val imagen: String,
    @SerializedName("descripcion")
    val descripcion: String,
    @SerializedName("descripcionDetallada")
    val descripcionDetallada: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("reviews")
    val reviews: Int,
    @SerializedName("porciones")
    val porciones: String,
    @SerializedName("calorias")
    val calorias: String,
    @SerializedName("ingredientes")
    val ingredientes: String,
    @SerializedName("reseñas")
    val reseñas: List<ReviewDto>
)

