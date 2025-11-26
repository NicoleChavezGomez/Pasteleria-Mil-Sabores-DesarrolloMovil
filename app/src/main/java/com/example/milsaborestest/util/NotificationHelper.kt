package com.example.milsaborestest.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.milsaborestest.MainActivity
import com.example.milsaborestest.R

object NotificationHelper {
    private const val CHANNEL_ID = "cart_reminder_channel"
    private const val CHANNEL_NAME = "Recordatorios de Carrito"
    private const val CHANNEL_DESCRIPTION = "Notificaciones sobre productos en tu carrito"
    private const val NOTIFICATION_ID = 1001

    /**
     * Crea el canal de notificaciones (requerido para Android 8.0+)
     * Debe llamarse una vez al iniciar la aplicación
     */
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH // Mayor importancia para asegurar que se muestre
            ).apply {
                description = CHANNEL_DESCRIPTION
                enableVibration(true)
                enableLights(true)
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Muestra una notificación de recordatorio de carrito abandonado
     * @param context Contexto de la aplicación
     * @param itemCount Cantidad de items en el carrito
     */
    fun showCartReminderNotification(context: Context, itemCount: Int) {
        android.util.Log.d("NotificationHelper", "Intentando mostrar notificación. ItemCount: $itemCount")
        
        // Verificar permisos antes de mostrar notificación
        if (!hasNotificationPermission(context)) {
            android.util.Log.w("NotificationHelper", "No se puede mostrar notificación: permisos no concedidos")
            return
        }

        // Intent para abrir la app y navegar al carrito
        // Usar FLAG_ACTIVITY_SINGLE_TOP para no reiniciar la app si ya está abierta
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("navigate_to", "cart")
            action = "com.example.milsaborestest.NAVIGATE_TO_CART" // Acción única para identificar el intent
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Construir la notificación con mayor visibilidad
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Icono del sistema que siempre funciona
            .setContentTitle("¡Carrito abandonado!")
            .setContentText(
                if (itemCount == 1) {
                    "Tienes 1 producto en tu carrito. ¡No te lo pierdas!"
                } else {
                    "Tienes $itemCount productos en tu carrito. ¡No te los pierdas!"
                }
            )
            .setStyle(NotificationCompat.BigTextStyle().bigText(
                if (itemCount == 1) {
                    "Tienes 1 producto en tu carrito. ¡No te lo pierdas!"
                } else {
                    "Tienes $itemCount productos en tu carrito. ¡No te los pierdas!"
                }
            ))
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Prioridad alta
            .setDefaults(NotificationCompat.DEFAULT_ALL) // Sonido, vibración, etc.
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // Se cierra automáticamente al hacer click
            .build()

        // Mostrar la notificación
        try {
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
        } catch (e: SecurityException) {
            // Si no hay permisos, no hacer nada
            android.util.Log.e("NotificationHelper", "No se pudo mostrar la notificación: ${e.message}")
        }
    }

    /**
     * Verifica si la app tiene permisos para mostrar notificaciones
     */
    private fun hasNotificationPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ requiere permiso explícito
            val hasPermission = NotificationManagerCompat.from(context).areNotificationsEnabled()
            android.util.Log.d("NotificationHelper", "Permisos de notificación: $hasPermission")
            hasPermission
        } else {
            // Android 12 y anteriores: notificaciones habilitadas por defecto
            android.util.Log.d("NotificationHelper", "Android < 13, notificaciones habilitadas por defecto")
            true
        }
    }

    /**
     * Cancela la notificación de carrito abandonado
     */
    fun cancelCartReminderNotification(context: Context) {
        NotificationManagerCompat.from(context).cancel(NOTIFICATION_ID)
    }
}



