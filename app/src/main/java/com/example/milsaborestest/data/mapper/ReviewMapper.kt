package com.example.milsaborestest.data.mapper

import com.example.milsaborestest.data.local.database.ReviewEntity
import com.example.milsaborestest.data.local.dto.ReviewDto
import com.example.milsaborestest.domain.model.Review

fun ReviewEntity.toDomain(): Review {
    return Review(
        id = id,
        autor = autor,
        fecha = fecha,
        rating = rating,
        comentario = comentario,
        userId = userId
    )
}

fun Review.toEntity(productId: String): ReviewEntity {
    return ReviewEntity(
        id = id ?: 0,
        productId = productId,
        userId = userId,
        autor = autor,
        fecha = fecha,
        rating = rating,
        comentario = comentario
    )
}

fun ReviewDto.toDomain(): Review {
    return Review(
        autor = autor,
        fecha = fecha,
        rating = rating,
        comentario = comentario
    )
}

fun ReviewDto.toEntity(productId: String): ReviewEntity {
    return ReviewEntity(
        productId = productId,
        userId = null,
        autor = autor,
        fecha = fecha,
        rating = rating,
        comentario = comentario
    )
}


