package com.example.milsaborestest.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
    version = 6,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun userDao(): UserDao
    abstract fun purchaseDao(): PurchaseDao
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        // Migración de versión 2 a 3: Agregar campo fotoPerfil a la tabla usuario
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE usuario ADD COLUMN fotoPerfil TEXT")
            }
        }
        
        // Migración de versión 3 a 4: Agregar tablas de compras e items de compra
        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Crear tabla de compras
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS compras (
                        id TEXT PRIMARY KEY NOT NULL,
                        userId INTEGER NOT NULL,
                        fecha TEXT NOT NULL,
                        total INTEGER NOT NULL,
                        estado TEXT NOT NULL,
                        FOREIGN KEY(userId) REFERENCES usuario(id) ON DELETE CASCADE
                    )
                """.trimIndent())
                
                // Crear tabla de items de compra
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS purchase_items (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        purchaseId TEXT NOT NULL,
                        productId TEXT NOT NULL,
                        nombre TEXT NOT NULL,
                        precio INTEGER NOT NULL,
                        cantidad INTEGER NOT NULL,
                        imagen TEXT NOT NULL,
                        FOREIGN KEY(purchaseId) REFERENCES compras(id) ON DELETE CASCADE
                    )
                """.trimIndent())
            }
        }
        
        // Migración de versión 5 a 6: Agregar tablas de categorías y productos
        val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Crear tabla de categorías
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS categoria (
                        id TEXT PRIMARY KEY NOT NULL,
                        nombre TEXT NOT NULL,
                        icono TEXT NOT NULL
                    )
                """.trimIndent())
                
                // Crear tabla de productos
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS producto (
                        id TEXT PRIMARY KEY NOT NULL,
                        nombre TEXT NOT NULL,
                        precio INTEGER NOT NULL,
                        imagen TEXT NOT NULL,
                        descripcion TEXT NOT NULL,
                        descripcionDetallada TEXT NOT NULL,
                        rating REAL NOT NULL,
                        reviews INTEGER NOT NULL,
                        porciones TEXT NOT NULL,
                        calorias TEXT NOT NULL,
                        ingredientes TEXT NOT NULL,
                        categoryId TEXT NOT NULL,
                        FOREIGN KEY(categoryId) REFERENCES categoria(id) ON DELETE CASCADE
                    )
                """.trimIndent())
                
                // Crear índice para categoryId
                database.execSQL("CREATE INDEX IF NOT EXISTS index_producto_categoryId ON producto(categoryId)")
            }
        }
        
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        val database = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "milsabores_database"
                        )
                        .addMigrations(MIGRATION_2_3, MIGRATION_3_4, MIGRATION_5_6)
                        .fallbackToDestructiveMigration() // Para desarrollo, permite recrear DB en cambios de versión
                        .build()
                        
                        INSTANCE = database
                        
                        // Insertar datos por defecto después de construir la base de datos
                        CoroutineScope(Dispatchers.IO).launch {
                            insertarDatosPorDefecto(database, context.applicationContext)
                        }
                    }
                }
            }
            return INSTANCE!!
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

