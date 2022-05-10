package com.eversadclown.borutoapplication.domain.repository

import kotlinx.coroutines.flow.Flow


//интерфейс для взаимодействия с данными самого приложения
interface DataStoreOperations {

    suspend fun saveOnBoardingState(completed: Boolean)
    fun readOnBoardingState() : Flow<Boolean>

}