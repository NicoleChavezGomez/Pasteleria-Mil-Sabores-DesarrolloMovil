package com.example.milsaborestest.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaborestest.data.local.database.AppDatabase
import com.example.milsaborestest.data.local.database.ReviewEntity
import com.example.milsaborestest.data.mapper.toDomain
import com.example.milsaborestest.domain.model.Review
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReviewViewModel(application: Application) : AndroidViewModel(application) {

    private val reviewDao = AppDatabase.getDatabase(application).reviewDao()

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews.asStateFlow()

    private val _averageRating = MutableStateFlow(0.0)
    val averageRating: StateFlow<Double> = _averageRating.asStateFlow()

    private val _reviewCount = MutableStateFlow(0)
    val reviewCount: StateFlow<Int> = _reviewCount.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    private val _userHasReview = MutableStateFlow(false)
    val userHasReview: StateFlow<Boolean> = _userHasReview.asStateFlow()

    private var currentProductId: String? = null

    fun loadReviews(productId: String, userId: Int? = null) {
        currentProductId = productId
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val entities = reviewDao.obtenerPorProducto(productId)
                _reviews.value = entities.map { it.toDomain() }
                _averageRating.value = reviewDao.obtenerRatingPromedio(productId) ?: 0.0
                _reviewCount.value = reviewDao.obtenerCantidadReseñas(productId)
                _userHasReview.value = if (userId != null) {
                    reviewDao.obtenerPorProductoYUsuario(productId, userId) != null
                } else {
                    false
                }
            } catch (e: Exception) {
                _message.value = "Error al cargar reseñas: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshUserReviewStatus(userId: Int?) {
        val productId = currentProductId ?: return
        if (userId == null) {
            _userHasReview.value = false
            return
        }

        viewModelScope.launch {
            _userHasReview.value = reviewDao.obtenerPorProductoYUsuario(productId, userId) != null
        }
    }

    fun addReview(
        productId: String,
        userId: Int,
        authorName: String,
        rating: Int,
        comment: String
    ) {
        viewModelScope.launch {
            if (rating !in 1..5) {
                _message.value = "Selecciona una calificación entre 1 y 5"
                return@launch
            }

            if (comment.isBlank()) {
                _message.value = "El comentario no puede estar vacío"
                return@launch
            }

            _isSubmitting.value = true

            try {
                val existing = reviewDao.obtenerPorProductoYUsuario(productId, userId)
                if (existing != null) {
                    _message.value = "Ya agregaste una reseña para este producto"
                    _userHasReview.value = true
                    return@launch
                }

                val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale("es", "CL"))
                val reviewEntity = ReviewEntity(
                    productId = productId,
                    userId = userId,
                    autor = authorName,
                    fecha = dateFormat.format(Date()),
                    rating = rating,
                    comentario = comment.trim()
                )

                reviewDao.insertar(reviewEntity)
                _message.value = "¡Gracias por tu reseña!"
                loadReviews(productId, userId)
            } catch (e: Exception) {
                _message.value = "Error al guardar reseña: ${e.message}"
            } finally {
                _isSubmitting.value = false
            }
        }
    }

    fun clearMessage() {
        _message.value = null
    }
}


