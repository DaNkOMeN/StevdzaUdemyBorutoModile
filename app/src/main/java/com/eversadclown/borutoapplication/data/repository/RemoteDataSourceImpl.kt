@file:OptIn(ExperimentalPagingApi::class)

package com.eversadclown.borutoapplication.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.eversadclown.borutoapplication.data.local.BorutoDatabase
import com.eversadclown.borutoapplication.data.paging_source.HeroRemoteMediator
import com.eversadclown.borutoapplication.data.remote.BorutoApi
import com.eversadclown.borutoapplication.domain.model.Hero
import com.eversadclown.borutoapplication.domain.repository.RemoteDataSource
import com.eversadclown.borutoapplication.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow


//класс для получения данных как с базы данных так и с сети
class RemoteDataSourceImpl(
    private val borutoApi: BorutoApi,
    private val borutoDatabase: BorutoDatabase
) : RemoteDataSource {

    private val heroDao = borutoDatabase.heroDao()

    override fun getAllHeroes(): Flow<PagingData<Hero>> {
        val pagingSourceFactory = { heroDao.getAllHeroes() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = HeroRemoteMediator(
                borutoApi = borutoApi,
                borutoDatabase = borutoDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchHeroes(): Flow<PagingData<Hero>> {
        TODO("Not yet implemented")
    }
}