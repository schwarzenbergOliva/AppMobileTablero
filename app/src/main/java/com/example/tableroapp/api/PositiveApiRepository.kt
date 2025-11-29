package com.example.tableroapp.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repositorio para manejar las llamadas a la API Positive API
 * Encapsula la lógica de negocio y manejo de errores
 */
object PositiveApiRepository {
    
    /**
     * Obtiene una frase positiva en español
     * @return Result con la frase o un error
     */
    suspend fun getPositivePhrase(): Result<PositivePhraseResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.positiveApiService.getPositivePhrase()
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    /**
     * Obtiene una frase positiva en inglés
     */
    suspend fun getPositivePhraseEng(): Result<PositivePhraseResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.positiveApiService.getPositivePhraseEng()
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    /**
     * Obtiene una frase por ID
     */
    suspend fun getPhraseById(id: Int): Result<PositivePhraseResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.positiveApiService.getPhraseById(id)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}

