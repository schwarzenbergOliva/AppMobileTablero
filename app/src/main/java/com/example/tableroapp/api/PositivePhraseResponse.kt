package com.example.tableroapp.api

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos para la respuesta de la API Positive API
 * URL: https://www.positive-api.online/phrase/esp
 */
data class PositivePhraseResponse(
    val id: Int,
    val text: String,
    val lang: String,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("author_id")
    val authorId: Int?
)

