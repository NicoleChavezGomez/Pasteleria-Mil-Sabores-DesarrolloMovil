package com.example.milsaborestest.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        CartEntity::class,
        UserEntity::class,
        PurchaseEntity::class,
        PurchaseItemEntity::class
    ],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun userDao(): UserDao
    abstract fun purchaseDao(): PurchaseDao
    
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
        
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        val database = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "milsabores_database"
                        )
                        .addMigrations(MIGRATION_2_3, MIGRATION_3_4)
                        .fallbackToDestructiveMigration() // Para desarrollo, permite recrear DB en cambios de versión
                        .build()
                        
                        INSTANCE = database
                        
                        // Insertar datos por defecto después de construir la base de datos
                        CoroutineScope(Dispatchers.IO).launch {
                            insertarDatosPorDefecto(database)
                        }
                    }
                }
            }
            return INSTANCE!!
        }
        
        private suspend fun insertarDatosPorDefecto(db: AppDatabase) {
            val userDao = db.userDao()
            
            // Verificar si ya existen usuarios para no duplicar
            val usuariosExistentes = userDao.obtenerTodos()
            if (usuariosExistentes.isNotEmpty()) {
                return // Ya hay usuarios, no insertar de nuevo
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
    }
}

