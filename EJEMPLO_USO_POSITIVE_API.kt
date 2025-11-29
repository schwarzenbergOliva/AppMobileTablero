// EJEMPLO DE USO DE LA API POSITIVE API
// Este archivo es solo de referencia, no se compila

package com.example.tableroapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.tableroapp.api.PositiveApiRepository
import com.example.tableroapp.api.PositivePhraseResponse

/**
 * EJEMPLO 1: Obtener una frase positiva en un Composable
 */
@Composable
fun EjemploUsoPositiveApi() {
    var frase by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    
    LaunchedEffect(Unit) {
        isLoading = true
        val result = PositiveApiRepository.getPositivePhrase()
        
        result.onSuccess { response ->
            frase = response.text
            error = null
        }.onFailure { exception ->
            error = exception.message ?: "Error desconocido"
            frase = null
        }
        
        isLoading = false
    }
    
    // Mostrar la frase en la UI
    if (isLoading) {
        Text("Cargando frase positiva...")
    } else if (error != null) {
        Text("Error: $error")
    } else if (frase != null) {
        Text("Frase positiva: $frase")
    }
}

/**
 * EJEMPLO 2: Usar la frase como descripción de una card
 */
@Composable
fun EjemploCardConFrasePositiva() {
    var frasePositiva by remember { mutableStateOf<String?>(null) }
    
    LaunchedEffect(Unit) {
        PositiveApiRepository.getPositivePhrase()
            .onSuccess { response ->
                frasePositiva = response.text
            }
    }
    
    // Crear una card con la frase positiva como descripción
    Card {
        Column {
            Text("Mi Tarea")
            if (frasePositiva != null) {
                Text(frasePositiva!!)
            } else {
                Text("Cargando frase...")
            }
        }
    }
}

/**
 * EJEMPLO 3: Obtener múltiples frases
 */
suspend fun obtenerFrasesPositivas(cantidad: Int): List<String> {
    val frases = mutableListOf<String>()
    
    repeat(cantidad) {
        PositiveApiRepository.getPositivePhrase()
            .onSuccess { response ->
                frases.add(response.text)
            }
    }
    
    return frases
}

/**
 * EJEMPLO 4: Usar en un ViewModel o función suspend
 */
suspend fun ejemploEnFuncionSuspend(): String? {
    return PositiveApiRepository.getPositivePhrase()
        .getOrNull()
        ?.text
}

