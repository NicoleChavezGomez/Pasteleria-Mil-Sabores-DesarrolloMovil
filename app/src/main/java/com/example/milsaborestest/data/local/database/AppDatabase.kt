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
        UserEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun userDao(): UserDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        val database = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "milsabores_database"
                        )
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

