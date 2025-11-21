package com.example.milsaborestest.data.local.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para Review desde JSON
 */
data class ReviewDto(
    @SerializedName("autor")
    val autor: String,
    @SerializedName("fecha")
    val fecha: String,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("comentario")
    val comentario: String
)

