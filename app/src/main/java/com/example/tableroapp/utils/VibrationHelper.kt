package com.example.tableroapp.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

/**
 * Helper para manejar la vibración del dispositivo
 */
object VibrationHelper {
    
    /**
     * Hace vibrar el dispositivo
     * @param context Contexto de la aplicación
     * @param durationMillis Duración de la vibración en milisegundos (por defecto 200ms)
     */
    fun vibrate(context: Context, durationMillis: Long = 200) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Android 12+ (API 31+)
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            // Versiones anteriores
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Android 8.0+ (API 26+)
            val vibrationEffect = VibrationEffect.createOneShot(durationMillis, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        } else {
            // Versiones anteriores
            @Suppress("DEPRECATION")
            vibrator.vibrate(durationMillis)
        }
    }
    
    /**
     * Hace vibrar el dispositivo con un patrón personalizado
     * @param context Contexto de la aplicación
     * @param pattern Patrón de vibración (array de tiempos: espera, vibrar, espera, vibrar...)
     * @param repeat Índice desde donde repetir el patrón (-1 para no repetir)
     */
    fun vibratePattern(context: Context, pattern: LongArray, repeat: Int = -1) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val vibrationEffect = VibrationEffect.createWaveform(pattern, repeat)
            vibrator.vibrate(vibrationEffect)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(pattern, repeat)
        }
    }
}

