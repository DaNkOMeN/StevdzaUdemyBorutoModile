package com.eversadclown.borutoapplication.data.repository

import com.eversadclown.borutoapplication.domain.repository.DataStoreOperations
import com.eversadclown.borutoapplication.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//репозиторий в котором происходят доступ ко всем данным прилоежния
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val dataStore: DataStoreOperations
) {

    //получить данные по персонажам из всех возможных ресурсов (база данных и сеть)
    fun getAllHeroes() =
        remoteDataSource.getAllHeroes()

    //сохранить состояние просмотренного стартового экрана
    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.saveOnBoardingState(completed = completed)
    }

    //прочитать состояние просмортенного стартового экрана
    fun readOnBoardingState() : Flow<Boolean> {
        return dataStore.readOnBoardingState()
    }

}