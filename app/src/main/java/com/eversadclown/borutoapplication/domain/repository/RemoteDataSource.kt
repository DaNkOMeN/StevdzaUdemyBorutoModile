package com.eversadclown.borutoapplication.domain.repository

import androidx.paging.PagingData
import com.eversadclown.borutoapplication.domain.model.Hero
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun getAllHeroes(): Flow<PagingData<Hero>>

    fun searchHeroes(): Flow<PagingData<Hero>>
}