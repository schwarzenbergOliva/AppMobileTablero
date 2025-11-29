package com.example.tableroapp.api

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interfaz de Retrofit para consumir la API Positive API
 * Base URL: https://www.positive-api.online/
 */
interface PositiveApiService {
    
    /**
     * Obtiene una frase positiva en español
     * Endpoint: /phrase/esp
     * @return PositivePhraseResponse con la frase positiva
     */
    @GET("phrase/esp")
    suspend fun getPositivePhrase(): PositivePhraseResponse
    
    /**
     * Obtiene una frase positiva en inglés
     * Endpoint: /phrase/eng
     */
    @GET("phrase/eng")
    suspend fun getPositivePhraseEng(): PositivePhraseResponse
    
    /**
     * Obtiene una frase positiva por ID
     * Endpoint: /phrase/{id}
     */
    @GET("phrase/{id}")
    suspend fun getPhraseById(@Path("id") id: Int): PositivePhraseResponse
}

