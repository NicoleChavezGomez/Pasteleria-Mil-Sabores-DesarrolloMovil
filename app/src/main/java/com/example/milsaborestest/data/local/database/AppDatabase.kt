package com.example.milsaborestest.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.milsaborestest.data.source.local.ProductJsonDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        CartEntity::class,
        UserEntity::class,
        PurchaseEntity::class,
        PurchaseItemEntity::class,
        CategoryEntity::class,
        ProductEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun userDao(): UserDao
    abstract fun purchaseDao(): PurchaseDao
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao
    
    companion object {
        private var database: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "milsabores_database"
                )
                .fallbackToDestructiveMigration() // Eliminar la base de datos al cambiar la versión
                .build()
                
                // Insertar datos por defecto después de construir la base de datos
                CoroutineScope(Dispatchers.IO).launch {
                    insertarDatosPorDefecto(database!!, context.applicationContext)
                }
            }
            return database!!
        }
        
        private suspend fun insertarDatosPorDefecto(db: AppDatabase, context: Context) {
            val userDao = db.userDao()
            val categoryDao = db.categoryDao()
            val productDao = db.productDao()
            
            // Verificar si ya existen usuarios para no duplicar
            val usuariosExistentes = userDao.obtenerTodos()
            if (usuariosExistentes.isEmpty()) {
                val usuarios = listOf(
                    UserEntity(
                        nombre = "Admin Mil Sabores",
                        email = "admin@milsabores.com",
                        contrasena = "123456"
                    ),
                    UserEntity(
                        nombre = "Cliente Demo",
                        email = "cliente@milsabores.com",
                        contrasena = "123456"
                    ),
                    UserEntity(
                        nombre = "Usuario Test",
                        email = "test@milsabores.com",
                        contrasena = "123456"
                    )
                )
                
                usuarios.forEach { userDao.insertar(it) }
            }
            
            // Verificar si ya existen categorías para no duplicar
            val categoriasExistentes = categoryDao.contar()
            if (categoriasExistentes == 0) {
                // Cargar productos y categorías desde JSON
                val jsonDataSource = ProductJsonDataSource(context)
                
                try {
                    val productosData = jsonDataSource.getProductosData().getOrNull()
                    if (productosData != null) {
                        // Insertar categorías
                        val categorias = productosData.categorias.map { (id, categoryDto) ->
                            CategoryEntity(
                                id = id,
                                nombre = categoryDto.nombre,
                                icono = categoryDto.icono
                            )
                        }
                        categoryDao.insertarTodas(categorias)
                        
                        // Insertar productos
                        val productos = productosData.categorias.flatMap { (categoryId, categoryDto) ->
                            categoryDto.productos.map { productDto ->
                                ProductEntity(
                                    id = productDto.id,
                                    nombre = productDto.nombre,
                                    precio = productDto.precio,
                                    imagen = productDto.imagen,
                                    descripcion = productDto.descripcion,
                                    descripcionDetallada = productDto.descripcionDetallada,
                                    rating = productDto.rating,
                                    reviews = productDto.reviews,
                                    porciones = productDto.porciones,
                                    calorias = productDto.calorias,
                                    ingredientes = productDto.ingredientes,
                                    categoryId = categoryId
                                )
                            }
                        }
                        productDao.insertarTodos(productos)
                    }
                } catch (e: Exception) {
                    // Error al cargar productos desde JSON, continuar sin ellos
                    // Los productos se pueden cargar más tarde
                }
            }
        }
    }
}

