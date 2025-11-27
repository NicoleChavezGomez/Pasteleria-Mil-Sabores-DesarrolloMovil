package com.example.milsaborestest.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaborestest.data.local.database.AppDatabase
import com.example.milsaborestest.data.local.database.PurchaseEntity
import com.example.milsaborestest.data.local.database.PurchaseItemEntity
import com.example.milsaborestest.domain.model.CartItem
import com.example.milsaborestest.domain.model.Purchase
import com.example.milsaborestest.domain.model.PurchaseItem
import com.example.milsaborestest.util.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * ViewModel para gestionar las compras y el historial de compras.
 * 
 * Maneja la lógica de checkout (simular compra) y la obtención del historial
 * de compras de un usuario.
 */
class PurchaseViewModel(application: Application) : AndroidViewModel(application) {
    
    private val database = AppDatabase.getDatabase(application)
    private val purchaseDao = database.purchaseDao()
    
    // Estado del historial de compras
    private val _purchaseHistory = MutableStateFlow<List<Purchase>>(emptyList())
    val purchaseHistory: StateFlow<List<Purchase>> = _purchaseHistory.asStateFlow()
    
    // Estado de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    // Mensajes de éxito/error
    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()
    
    // Compra actual (para mostrar detalles después del checkout)
    private val _currentPurchase = MutableStateFlow<Purchase?>(null)
    val currentPurchase: StateFlow<Purchase?> = _currentPurchase.asStateFlow()
    
    /**
     * Realiza el proceso de checkout: crea una compra con los items del carrito.
     * 
     * @param cartItems Lista de items del carrito a comprar
     * @param userId ID del usuario que realiza la compra
     * @return ID de la compra creada o null si hubo error
     */
    suspend fun realizarCompra(cartItems: List<CartItem>, userId: Int): String? {
        if (cartItems.isEmpty()) {
            _message.value = "El carrito está vacío"
            return null
        }
        
        _isLoading.value = true
        
        return try {
            // Generar ID único para la compra (UUID)
            val purchaseId = UUID.randomUUID().toString()
            
            // Obtener fecha actual en formato ISO 8601
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val fecha = dateFormat.format(Date())
            
            // Calcular total
            val total = cartItems.sumOf { it.precio * it.cantidad }
            
            // Crear entidad de compra
            val purchaseEntity = PurchaseEntity(
                id = purchaseId,
                userId = userId,
                fecha = fecha,
                total = total,
                estado = "completada"
            )
            
            // Crear entidades de items de compra
            val purchaseItemEntities = cartItems.map { cartItem ->
                PurchaseItemEntity(
                    purchaseId = purchaseId,
                    productId = cartItem.id,
                    nombre = cartItem.nombre,
                    precio = cartItem.precio,
                    cantidad = cartItem.cantidad,
                    imagen = cartItem.imagen
                )
            }
            
            // Insertar en base de datos
            purchaseDao.insertarCompra(purchaseEntity)
            purchaseDao.insertarItems(purchaseItemEntities)
            
            // Crear modelo de dominio para la compra actual
            val purchaseItems = purchaseItemEntities.map { entity ->
                PurchaseItem(
                    id = entity.id,
                    productId = entity.productId,
                    nombre = entity.nombre,
                    precio = entity.precio,
                    cantidad = entity.cantidad,
                    imagen = entity.imagen
                )
            }
            
            _currentPurchase.value = Purchase(
                id = purchaseId,
                userId = userId,
                fecha = formatearFecha(fecha),
                total = total,
                estado = "completada",
                items = purchaseItems
            )
            
            _message.value = "¡Compra realizada con éxito!"
            Log.d(Constants.TAG, "Compra realizada: ID=$purchaseId, Total=$total, Items=${cartItems.size}")
            
            purchaseId
        } catch (e: Exception) {
            _message.value = "Error al realizar la compra: ${e.message}"
            Log.e(Constants.TAG, "Error al realizar compra", e)
            null
        } finally {
            _isLoading.value = false
        }
    }
    
    /**
     * Obtiene el historial de compras de un usuario.
     * 
     * @param userId ID del usuario
     */
    fun obtenerHistorialCompras(userId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val purchaseEntities = purchaseDao.obtenerComprasPorUsuario(userId)
                
                // Convertir a modelos de dominio con sus items
                val purchases = purchaseEntities.map { purchaseEntity ->
                    val itemEntities = purchaseDao.obtenerItemsPorCompra(purchaseEntity.id)
                    val items = itemEntities.map { itemEntity ->
                        PurchaseItem(
                            id = itemEntity.id,
                            productId = itemEntity.productId,
                            nombre = itemEntity.nombre,
                            precio = itemEntity.precio,
                            cantidad = itemEntity.cantidad,
                            imagen = itemEntity.imagen
                        )
                    }
                    
                    Purchase(
                        id = purchaseEntity.id,
                        userId = purchaseEntity.userId,
                        fecha = formatearFecha(purchaseEntity.fecha),
                        total = purchaseEntity.total,
                        estado = purchaseEntity.estado,
                        items = items
                    )
                }
                
                _purchaseHistory.value = purchases
                Log.d(Constants.TAG, "Historial de compras cargado: ${purchases.size} compras")
            } catch (e: Exception) {
                _message.value = "Error al cargar historial: ${e.message}"
                Log.e(Constants.TAG, "Error al cargar historial de compras", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Obtiene una compra específica por su ID.
     * 
     * @param purchaseId ID de la compra
     */
    fun obtenerCompraPorId(purchaseId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val purchaseEntity = purchaseDao.obtenerCompraPorId(purchaseId)
                if (purchaseEntity != null) {
                    val itemEntities = purchaseDao.obtenerItemsPorCompra(purchaseEntity.id)
                    val items = itemEntities.map { itemEntity ->
                        PurchaseItem(
                            id = itemEntity.id,
                            productId = itemEntity.productId,
                            nombre = itemEntity.nombre,
                            precio = itemEntity.precio,
                            cantidad = itemEntity.cantidad,
                            imagen = itemEntity.imagen
                        )
                    }
                    
                    _currentPurchase.value = Purchase(
                        id = purchaseEntity.id,
                        userId = purchaseEntity.userId,
                        fecha = formatearFecha(purchaseEntity.fecha),
                        total = purchaseEntity.total,
                        estado = purchaseEntity.estado,
                        items = items
                    )
                } else {
                    _message.value = "Compra no encontrada"
                }
            } catch (e: Exception) {
                _message.value = "Error al cargar compra: ${e.message}"
                Log.e(Constants.TAG, "Error al cargar compra por ID", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Limpia el mensaje actual.
     */
    fun clearMessage() {
        _message.value = null
    }
    
    /**
     * Limpia la compra actual.
     */
    fun clearCurrentPurchase() {
        _currentPurchase.value = null
    }
    
    /**
     * Formatea una fecha ISO 8601 a un formato más legible.
     * 
     * @param isoDate Fecha en formato ISO 8601 (ej: "2025-11-26T23:30:00")
     * @return Fecha formateada (ej: "26 Nov 2025, 23:30")
     */
    private fun formatearFecha(isoDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("es", "CL"))
            val date = inputFormat.parse(isoDate)
            date?.let { outputFormat.format(it) } ?: isoDate
        } catch (e: Exception) {
            isoDate // Si hay error, devolver la fecha original
        }
    }
}
