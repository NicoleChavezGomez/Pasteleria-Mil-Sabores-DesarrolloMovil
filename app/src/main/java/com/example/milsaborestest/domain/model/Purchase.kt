package com.example.milsaborestest.domain.model

/**
 * Modelo de dominio que representa una compra/pedido completo.
 * 
 * Este modelo se usa en la capa de presentación y lógica de negocio.
 * Contiene toda la información de una compra incluyendo sus items.
 * 
 * @property id ID único de la compra
 * @property userId ID del usuario que realizó la compra
 * @property fecha Fecha y hora de la compra en formato legible
 * @property total Total de la compra en pesos chilenos
 * @property estado Estado de la compra (ej: "completada", "pendiente", "cancelada")
 * @property items Lista de items/productos comprados
 */
data class Purchase(
    val id: String,
    val userId: Int,
    val fecha: String,
    val total: Int,
    val estado: String = "completada",
    val items: List<PurchaseItem> = emptyList()
)

/**
 * Modelo de dominio que representa un item individual dentro de una compra.
 * 
 * Contiene un snapshot del producto al momento de la compra para mantener
 * un historial preciso incluso si el producto cambia posteriormente.
 * 
 * @property id ID único del item
 * @property productId ID del producto original
 * @property nombre Nombre del producto al momento de la compra
 * @property precio Precio unitario del producto
 * @property cantidad Cantidad de unidades compradas
 * @property imagen URL de la imagen del producto
 * @property subtotal Subtotal calculado (precio * cantidad)
 */
data class PurchaseItem(
    val id: Int = 0,
    val productId: String,
    val nombre: String,
    val precio: Int,
    val cantidad: Int,
    val imagen: String
) {
    /**
     * Calcula el subtotal de este item (precio * cantidad).
     */
    val subtotal: Int
        get() = precio * cantidad
}
