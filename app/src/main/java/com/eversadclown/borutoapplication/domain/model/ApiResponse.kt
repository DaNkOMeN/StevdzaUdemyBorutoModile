package com.eversadclown.borutoapplication.domain.model

import kotlinx.serialization.Serializable


//ответ который прилетает с сервака. Получаем его с помощью запросов
@Serializable
data class ApiResponse(
    val success: Boolean,
    val message: String? = null,
    val prevPage: Int? = null,
    val nextPage: Int? = null,
    val heroes: List<Hero> = emptyList(),
    val lastUpdated: Long? = null
)
