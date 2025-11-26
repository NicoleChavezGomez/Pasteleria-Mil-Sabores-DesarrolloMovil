package com.example.milsaborestest.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaborestest.data.local.database.AppDatabase
import com.example.milsaborestest.data.repository.CartRepositoryImpl
import com.example.milsaborestest.data.repository.ProductRepositoryImpl
import com.example.milsaborestest.data.source.local.ProductJsonDataSource
import com.example.milsaborestest.domain.model.CartItem
import com.example.milsaborestest.domain.repository.CartRepository
import com.example.milsaborestest.domain.repository.ProductRepository
import com.example.milsaborestest.util.Constants
import com.example.milsaborestest.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SnackbarMessage(
    val message: String,
    val actionLabel: String? = null,
    val onAction: (suspend () -> Unit)? = null
)

class CartViewModel(application: Application) : AndroidViewModel(application) {
    
    private val database = AppDatabase.getDatabase(application)
    private val cartDao = database.cartDao()
    private val cartRepository: CartRepository = CartRepositoryImpl(cartDao)
    
    private val productDataSource = ProductJsonDataSource(application)
    private val productRepository: ProductRepository = ProductRepositoryImpl(productDataSource)
    
    val cartItems: StateFlow<List<CartItem>> = cartRepository.getAllCartItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    val totalItems: StateFlow<Int> = cartRepository.getCartItemCount()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    
    val totalPrice: StateFlow<Int> = cartRepository.getCartTotalPrice()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    
    private val _snackbarMessage = MutableStateFlow<SnackbarMessage?>(null)
    val snackbarMessage: StateFlow<SnackbarMessage?> = _snackbarMessage.asStateFlow()
    
    private val _lastAddedProduct = MutableStateFlow<CartItem?>(null)
    
    fun addToCart(productId: String, quantity: Int = 1) {
        viewModelScope.launch {
            try {
                Log.d(Constants.TAG, "addToCart llamado: productId=$productId, quantity=$quantity")
                val product = productRepository.getProductById(productId)
                if (product != null) {
                    val cartItem = CartItem(
                        id = product.id,
                        nombre = product.nombre,
                        precio = product.precio,
                        cantidad = quantity,
                        imagen = product.imagen,
                        descripcion = product.descripcion
                    )
                    cartRepository.addToCart(cartItem)
                    _lastAddedProduct.value = cartItem
                    
                    // Mostrar Snackbar con acción de deshacer
                    val message = "${product.nombre} agregado al carrito"
                    Log.d(Constants.TAG, "Enviando mensaje de Snackbar: $message")
                    _snackbarMessage.value = SnackbarMessage(
                        message = message,
                        actionLabel = "Deshacer",
                        onAction = { undoAddToCart() }
                    )
                } else {
                    Log.e(Constants.TAG, "Producto no encontrado: $productId")
                    _snackbarMessage.value = SnackbarMessage("Producto no encontrado")
                }
            } catch (e: Exception) {
                Log.e(Constants.TAG, "Error al agregar producto", e)
                _snackbarMessage.value = SnackbarMessage("Error al agregar producto: ${e.message}")
            }
        }
    }
    
    private suspend fun undoAddToCart() {
        try {
            _lastAddedProduct.value?.let { cartItem ->
                cartRepository.removeFromCart(cartItem.id)
                _snackbarMessage.value = SnackbarMessage("Producto eliminado del carrito")
            }
        } catch (e: Exception) {
            _snackbarMessage.value = SnackbarMessage("Error al deshacer")
        }
    }
    
    fun updateQuantity(productId: String, newQuantity: Int) {
        viewModelScope.launch {
            try {
                val cartItem = cartRepository.getCartItemById(productId)
                if (cartItem != null) {
                    cartRepository.updateCartItem(cartItem.copy(cantidad = newQuantity.coerceIn(1, 99)))
                    _snackbarMessage.value = SnackbarMessage("Cantidad actualizada")
                }
            } catch (e: Exception) {
                _snackbarMessage.value = SnackbarMessage("Error al actualizar cantidad: ${e.message}")
            }
        }
    }
    
    fun removeFromCart(productId: String) {
        viewModelScope.launch {
            try {
                cartRepository.removeFromCart(productId)
                _snackbarMessage.value = SnackbarMessage("Producto eliminado del carrito")
            } catch (e: Exception) {
                _snackbarMessage.value = SnackbarMessage("Error al eliminar producto: ${e.message}")
            }
        }
    }
    
    fun clearCart() {
        viewModelScope.launch {
            try {
                cartRepository.clearCart()
                _snackbarMessage.value = SnackbarMessage("Carrito vaciado")
                // Cancelar notificación si existe
                com.example.milsaborestest.util.NotificationHelper.cancelCartReminderNotification(getApplication())
            } catch (e: Exception) {
                _snackbarMessage.value = SnackbarMessage("Error al vaciar carrito: ${e.message}")
            }
        }
    }
    
    fun clearSnackbarMessage() {
        _snackbarMessage.value = null
    }
}

