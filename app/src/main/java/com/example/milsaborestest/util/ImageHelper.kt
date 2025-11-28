package com.example.milsaborestest.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
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
     * Convierte un URI de galería a Bitmap, aplicando la rotación EXIF correcta
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
            
            // Decodificar bitmap
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            
            if (bitmap == null) {
                Log.e(TAG, "No se pudo decodificar el bitmap del URI: $uri")
                return null
            }
            
            // Obtener orientación EXIF y rotar si es necesario
            val rotatedBitmap = rotateImageIfRequired(context, bitmap, uri)
            
            rotatedBitmap
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
     * Rota la imagen según la orientación EXIF si es necesario
     */
    private fun rotateImageIfRequired(context: Context, bitmap: Bitmap, uri: Uri): Bitmap {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            if (inputStream == null) {
                return bitmap
            }
            
            val exif = ExifInterface(inputStream)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            inputStream.close()
            
            val matrix = Matrix()
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> {
                    matrix.postRotate(90f)
                }
                ExifInterface.ORIENTATION_ROTATE_180 -> {
                    matrix.postRotate(180f)
                }
                ExifInterface.ORIENTATION_ROTATE_270 -> {
                    matrix.postRotate(270f)
                }
                ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> {
                    matrix.postScale(-1f, 1f)
                }
                ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
                    matrix.postScale(1f, -1f)
                }
                ExifInterface.ORIENTATION_TRANSPOSE -> {
                    matrix.postRotate(90f)
                    matrix.postScale(-1f, 1f)
                }
                ExifInterface.ORIENTATION_TRANSVERSE -> {
                    matrix.postRotate(270f)
                    matrix.postScale(-1f, 1f)
                }
                else -> {
                    // ORIENTATION_NORMAL o desconocida, no rotar
                    return bitmap
                }
            }
            
            // Aplicar transformación
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        } catch (e: Exception) {
            Log.e(TAG, "Error al leer EXIF: ${e.message}")
            bitmap
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

            // Eliminar todas las imágenes de perfil anteriores de este usuario
            profileDir.listFiles()?.forEach { file ->
                if (file.name.startsWith("profile_${userId}_") || file.name == "profile_${userId}.$IMAGE_FORMAT") {
                    file.delete()
                    Log.d(TAG, "Imagen antigua eliminada: ${file.name}")
                }
            }

            // Crear archivo con nombre único (incluye timestamp para evitar cache de Coil)
            val timestamp = System.currentTimeMillis()
            val imageFile = File(profileDir, "profile_${userId}_${timestamp}.$IMAGE_FORMAT")
            
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

