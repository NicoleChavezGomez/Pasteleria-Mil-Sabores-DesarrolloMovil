package com.example.milsaborestest.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaborestest.data.local.database.AppDatabase
import com.example.milsaborestest.data.repository.CartRepositoryImpl
import com.example.milsaborestest.data.repository.ProductRepositoryImpl
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
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.lifecycle.ViewModelProvider

data class SnackbarMessage(
    val message: String,
    val actionLabel: String? = null,
    val onAction: (suspend () -> Unit)? = null
)

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModel(application: Application) : AndroidViewModel(application) {
    
    private val database = AppDatabase.getDatabase(application)
    private val cartDao = database.cartDao()
    private val cartRepository: CartRepository = CartRepositoryImpl(cartDao)
    
    private val categoryDao = database.categoryDao()
    private val productDao = database.productDao()
    private val productRepository: ProductRepository = ProductRepositoryImpl(categoryDao, productDao)
    
    // Obtener AuthViewModel para acceder al usuario actual
    // Usamos un enfoque diferente: obtenemos el userId directamente cuando se necesita
    // Para los StateFlows, usamos un MutableStateFlow que se actualiza desde fuera
    private val _currentUserId = MutableStateFlow<Int?>(null)
    
    // Función helper para obtener el userId actual del AuthViewModel compartido
    private suspend fun getCurrentUserId(): Int? {
        // Intentar obtener el userId del AuthViewModel compartido
        // Como no podemos acceder directamente al ViewModel compartido desde aquí,
        // mantenemos el setUserId para que MainContent lo establezca
        return _currentUserId.value
    }
    
    val cartItems: StateFlow<List<CartItem>> = _currentUserId.flatMapLatest { userId ->
        if (userId != null) {
            cartRepository.getAllCartItems(userId)
        } else {
            kotlinx.coroutines.flow.flowOf(emptyList())
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    
    val totalItems: StateFlow<Int> = _currentUserId.flatMapLatest { userId ->
        if (userId != null) {
            cartRepository.getCartItemCount(userId)
        } else {
            kotlinx.coroutines.flow.flowOf(0)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )
    
    val totalPrice: StateFlow<Int> = _currentUserId.flatMapLatest { userId ->
        if (userId != null) {
            cartRepository.getCartTotalPrice(userId)
        } else {
            kotlinx.coroutines.flow.flowOf(0)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )
    
    // Función para establecer el userId desde MainContent
    // Esto es necesario porque cada pantalla crea su propia instancia de CartViewModel
    // y necesitamos sincronizar el userId desde MainContent
    fun setUserId(userId: Int?) {
        Log.d(Constants.TAG, "CartViewModel.setUserId llamado con: $userId")
        _currentUserId.value = userId
        Log.d(Constants.TAG, "CartViewModel.setUserId - _currentUserId actualizado a: ${_currentUserId.value}")
    }
    
    private val _snackbarMessage = MutableStateFlow<SnackbarMessage?>(null)
    val snackbarMessage: StateFlow<SnackbarMessage?> = _snackbarMessage.asStateFlow()
    
    private val _lastAddedProduct = MutableStateFlow<CartItem?>(null)
    
    fun addToCart(productId: String, quantity: Int = 1) {
        viewModelScope.launch {
            try {
                val userId = _currentUserId.value
                Log.d(Constants.TAG, "addToCart - userId actual: $userId, productId: $productId")
                if (userId == null) {
                    Log.e(Constants.TAG, "addToCart - userId es null, no se puede agregar al carrito")
                    _snackbarMessage.value = SnackbarMessage("Debes iniciar sesión para agregar productos al carrito")
                    return@launch
                }
                
                Log.d(Constants.TAG, "addToCart llamado: productId=$productId, quantity=$quantity, userId=$userId")
                val product = productRepository.getProductById(productId)
                Log.d(Constants.TAG, "addToCart - producto encontrado: ${product != null}, productId buscado: $productId")
                if (product != null) {
                    Log.d(Constants.TAG, "addToCart - producto: ${product.nombre}, precio: ${product.precio}")
                    val cartItem = CartItem(
                        id = product.id,
                        nombre = product.nombre,
                        precio = product.precio,
                        cantidad = quantity,
                        imagen = product.imagen,
                        descripcion = product.descripcion
                    )
                    cartRepository.addToCart(cartItem, userId)
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
            val userId = _currentUserId.value
            if (userId == null) return
            
            _lastAddedProduct.value?.let { cartItem ->
                cartRepository.removeFromCart(cartItem.id, userId)
                _snackbarMessage.value = SnackbarMessage("Producto eliminado del carrito")
            }
        } catch (e: Exception) {
            _snackbarMessage.value = SnackbarMessage("Error al deshacer")
        }
    }
    
    fun updateQuantity(productId: String, newQuantity: Int) {
        viewModelScope.launch {
            try {
                val userId = _currentUserId.value
                if (userId == null) return@launch
                
                val cartItem = cartRepository.getCartItemById(productId, userId)
                if (cartItem != null) {
                    cartRepository.updateCartItem(cartItem.copy(cantidad = newQuantity.coerceIn(1, 99)), userId)
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
                val userId = _currentUserId.value
                if (userId == null) return@launch
                
                cartRepository.removeFromCart(productId, userId)
                _snackbarMessage.value = SnackbarMessage("Producto eliminado del carrito")
            } catch (e: Exception) {
                _snackbarMessage.value = SnackbarMessage("Error al eliminar producto: ${e.message}")
            }
        }
    }
    
    fun clearCart() {
        viewModelScope.launch {
            try {
                val userId = _currentUserId.value
                if (userId == null) return@launch
                
                cartRepository.clearCart(userId)
                _snackbarMessage.value = SnackbarMessage("Carrito vaciado")
                // Cancelar notificación si existe
                com.example.milsaborestest.util.NotificationHelper.cancelCartReminderNotification(getApplication())
            } catch (e: Exception) {
                _snackbarMessage.value = SnackbarMessage("Error al vaciar carrito: ${e.message}")
            }
        }
    }
    
    fun clearCart(userId: Int) {
        viewModelScope.launch {
            try {
                cartRepository.clearCart(userId)
            } catch (e: Exception) {
                Log.e(Constants.TAG, "Error al limpiar carrito", e)
            }
        }
    }
    
    fun clearSnackbarMessage() {
        _snackbarMessage.value = null
    }
}

