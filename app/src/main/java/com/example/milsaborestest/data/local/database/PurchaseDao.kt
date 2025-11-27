package com.example.milsaborestest.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

/**
 * DAO (Data Access Object) para operaciones de base de datos relacionadas con compras.
 * 
 * Proporciona métodos para insertar y consultar compras y sus items asociados.
 */
@Dao
interface PurchaseDao {
    
    /**
     * Inserta una nueva compra en la base de datos.
     * 
     * @param purchase La compra a insertar
     * @return El ID de la fila insertada
     */
    @Insert
    suspend fun insertarCompra(purchase: PurchaseEntity): Long
    
    /**
     * Inserta múltiples items de compra en la base de datos.
     * 
     * @param items Lista de items a insertar
     */
    @Insert
    suspend fun insertarItems(items: List<PurchaseItemEntity>)
    
    /**
     * Obtiene todas las compras de un usuario específico, ordenadas por fecha descendente.
     * 
     * @param userId ID del usuario
     * @return Lista de compras del usuario (más recientes primero)
     */
    @Query("SELECT * FROM compras WHERE userId = :userId ORDER BY fecha DESC")
    suspend fun obtenerComprasPorUsuario(userId: Int): List<PurchaseEntity>
    
    /**
     * Obtiene una compra específica por su ID.
     * 
     * @param purchaseId ID de la compra
     * @return La compra encontrada o null si no existe
     */
    @Query("SELECT * FROM compras WHERE id = :purchaseId")
    suspend fun obtenerCompraPorId(purchaseId: String): PurchaseEntity?
    
    /**
     * Obtiene todos los items de una compra específica.
     * 
     * @param purchaseId ID de la compra
     * @return Lista de items de la compra
     */
    @Query("SELECT * FROM purchase_items WHERE purchaseId = :purchaseId")
    suspend fun obtenerItemsPorCompra(purchaseId: String): List<PurchaseItemEntity>
    
    /**
     * Obtiene el total de compras realizadas por un usuario.
     * 
     * @param userId ID del usuario
     * @return Cantidad total de compras
     */
    @Query("SELECT COUNT(*) FROM compras WHERE userId = :userId")
    suspend fun contarComprasPorUsuario(userId: Int): Int
    
    /**
     * Obtiene la suma total gastada por un usuario en todas sus compras.
     * 
     * @param userId ID del usuario
     * @return Total gastado en pesos chilenos
     */
    @Query("SELECT COALESCE(SUM(total), 0) FROM compras WHERE userId = :userId")
    suspend fun obtenerTotalGastadoPorUsuario(userId: Int): Int
}
