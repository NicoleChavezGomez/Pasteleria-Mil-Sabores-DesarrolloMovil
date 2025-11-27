package com.example.milsaborestest.data.local.database

import android.content.Context
import android.util.Log
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
        ReviewEntity::class
    ],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun userDao(): UserDao
    abstract fun purchaseDao(): PurchaseDao
    abstract fun reviewDao(): ReviewDao
    
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
        
        // Migración de versión 4 a 5: Crear tabla de reseñas
        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS reseñas (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        productId TEXT NOT NULL,
                        userId INTEGER,
                        autor TEXT NOT NULL,
                        fecha TEXT NOT NULL,
                        rating INTEGER NOT NULL,
                        comentario TEXT NOT NULL,
                        FOREIGN KEY(userId) REFERENCES usuario(id) ON DELETE CASCADE
                    )
                    """.trimIndent()
                )
                database.execSQL("CREATE INDEX IF NOT EXISTS index_reseñas_productId ON reseñas(productId)")
                database.execSQL("CREATE INDEX IF NOT EXISTS index_reseñas_userId ON reseñas(userId)")
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
                        .addMigrations(MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
                        .fallbackToDestructiveMigration() // Para desarrollo, permite recrear DB en cambios de versión
                        .build()
                        
                        INSTANCE = database
                        
                        // Insertar datos por defecto después de construir la base de datos
                        CoroutineScope(Dispatchers.IO).launch {
                            insertarDatosPorDefecto(context.applicationContext, database)
                        }
                    }
                }
            }
            return INSTANCE!!
        }
        
        private suspend fun insertarDatosPorDefecto(context: Context, db: AppDatabase) {
            insertarUsuariosPorDefecto(db.userDao())
            insertarReseñasPorDefecto(context, db.reviewDao())
        }

        private suspend fun insertarUsuariosPorDefecto(userDao: UserDao) {
            val usuariosExistentes = userDao.obtenerTodos()
            if (usuariosExistentes.isNotEmpty()) {
                return
            }

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

        private suspend fun insertarReseñasPorDefecto(
            context: Context,
            reviewDao: ReviewDao
        ) {
            val totalReseñas = reviewDao.contarReseñas()
            if (totalReseñas > 0) {
                return
            }

            try {
                val dataSource = ProductJsonDataSource(context)
                val productos = dataSource.getProductosData().getOrNull() ?: return
                val reseñas = productos.categorias.values.flatMap { category ->
                    category.productos.flatMap { product ->
                        product.reseñas.map { reviewDto ->
                            ReviewEntity(
                                productId = product.id,
                                userId = null,
                                autor = reviewDto.autor,
                                fecha = reviewDto.fecha,
                                rating = reviewDto.rating,
                                comentario = reviewDto.comentario
                            )
                        }
                    }
                }

                if (reseñas.isNotEmpty()) {
                    reviewDao.insertarTodas(reseñas)
                }
            } catch (e: Exception) {
                Log.e("AppDatabase", "Error insertando reseñas por defecto", e)
            }
        }
    }
}

