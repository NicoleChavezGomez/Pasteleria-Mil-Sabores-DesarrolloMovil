package com.example.milsaborestest.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Helper para manejar operaciones con imágenes de perfil
 * - Convertir URI a Bitmap
 * - Guardar imágenes en storage interno
 * - Cargar imágenes desde storage
 * - Eliminar imágenes antiguas
 */
object ImageHelper {
    private const val TAG = "ImageHelper"
    private const val PROFILE_IMAGE_DIR = "profile_images"
    private const val IMAGE_FORMAT = "jpg"
    private const val IMAGE_QUALITY = 90

    /**
     * Convierte un URI de galería a Bitmap
     * @param context Contexto de la aplicación
     * @param uri URI de la imagen seleccionada
     * @return Bitmap si la conversión fue exitosa, null en caso de error
     */
    fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            if (inputStream == null) {
                Log.e(TAG, "No se pudo abrir el input stream del URI: $uri")
                return null
            }
            
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            
            if (bitmap == null) {
                Log.e(TAG, "No se pudo decodificar el bitmap del URI: $uri")
            }
            
            bitmap
        } catch (e: FileNotFoundException) {
            Log.e(TAG, "Archivo no encontrado: ${e.message}")
            null
        } catch (e: IOException) {
            Log.e(TAG, "Error de IO al leer URI: ${e.message}")
            null
        } catch (e: Exception) {
            Log.e(TAG, "Error inesperado al convertir URI a Bitmap: ${e.message}")
            null
        }
    }

    /**
     * Guarda una imagen de perfil en storage interno
     * @param context Contexto de la aplicación
     * @param bitmap Bitmap a guardar
     * @param userId ID del usuario
     * @return Ruta del archivo guardado si fue exitoso, null en caso de error
     */
    fun saveProfileImage(context: Context, bitmap: Bitmap, userId: Int): String? {
        return try {
            // Crear directorio si no existe
            val profileDir = File(context.filesDir, PROFILE_IMAGE_DIR)
            if (!profileDir.exists()) {
                profileDir.mkdirs()
            }

            // Crear archivo con nombre único
            val imageFile = File(profileDir, "profile_${userId}.$IMAGE_FORMAT")
            
            // Guardar bitmap como JPEG
            val outputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY, outputStream)
            outputStream.flush()
            outputStream.close()
            
            Log.d(TAG, "Imagen guardada exitosamente: ${imageFile.absolutePath}")
            imageFile.absolutePath
        } catch (e: IOException) {
            Log.e(TAG, "Error de IO al guardar imagen: ${e.message}")
            null
        } catch (e: SecurityException) {
            Log.e(TAG, "Error de seguridad al guardar imagen: ${e.message}")
            null
        } catch (e: Exception) {
            Log.e(TAG, "Error inesperado al guardar imagen: ${e.message}")
            null
        }
    }

    /**
     * Carga una imagen de perfil desde storage interno
     * @param context Contexto de la aplicación
     * @param imagePath Ruta absoluta del archivo de imagen
     * @return Bitmap si la carga fue exitosa, null en caso de error o si el archivo no existe
     */
    fun loadProfileImage(context: Context, imagePath: String?): Bitmap? {
        if (imagePath == null || imagePath.isBlank()) {
            Log.d(TAG, "Ruta de imagen es null o vacía")
            return null
        }
        
        return try {
            val imageFile = File(imagePath)
            
            // Verificar que el archivo existe
            if (!imageFile.exists()) {
                Log.d(TAG, "Archivo de imagen no existe: $imagePath")
                return null
            }
            
            // Decodificar bitmap desde archivo
            val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
            
            if (bitmap == null) {
                Log.e(TAG, "No se pudo decodificar el bitmap desde: $imagePath")
                return null
            }
            
            Log.d(TAG, "Imagen cargada exitosamente desde: $imagePath")
            bitmap
        } catch (e: FileNotFoundException) {
            Log.e(TAG, "Archivo no encontrado: ${e.message}")
            null
        } catch (e: IOException) {
            Log.e(TAG, "Error de IO al cargar imagen: ${e.message}")
            null
        } catch (e: Exception) {
            Log.e(TAG, "Error inesperado al cargar imagen: ${e.message}")
            null
        }
    }

    /**
     * Elimina una imagen de perfil antigua del storage interno
     * @param context Contexto de la aplicación
     * @param imagePath Ruta absoluta del archivo de imagen a eliminar
     * @return true si se eliminó exitosamente o si no existía, false en caso de error
     */
    fun deleteProfileImage(context: Context, imagePath: String?): Boolean {
        if (imagePath == null || imagePath.isBlank()) {
            Log.d(TAG, "Ruta de imagen es null o vacía, no hay nada que eliminar")
            return true // No es un error si no hay ruta
        }
        
        return try {
            val imageFile = File(imagePath)
            
            // Si el archivo no existe, consideramos que ya está "eliminado"
            if (!imageFile.exists()) {
                Log.d(TAG, "Archivo no existe, no hay nada que eliminar: $imagePath")
                return true
            }
            
            // Intentar eliminar el archivo
            val deleted = imageFile.delete()
            
            if (deleted) {
                Log.d(TAG, "Imagen eliminada exitosamente: $imagePath")
            } else {
                Log.w(TAG, "No se pudo eliminar la imagen: $imagePath")
            }
            
            deleted
        } catch (e: SecurityException) {
            Log.e(TAG, "Error de seguridad al eliminar imagen: ${e.message}")
            false
        } catch (e: Exception) {
            Log.e(TAG, "Error inesperado al eliminar imagen: ${e.message}")
            false
        }
    }
}

