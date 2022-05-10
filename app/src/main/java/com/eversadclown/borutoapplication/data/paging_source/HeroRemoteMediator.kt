package com.eversadclown.borutoapplication.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.eversadclown.borutoapplication.data.local.BorutoDatabase
import com.eversadclown.borutoapplication.data.remote.BorutoApi
import com.eversadclown.borutoapplication.domain.model.Hero
import com.eversadclown.borutoapplication.domain.model.HeroRemoteKeys
import javax.inject.Inject


//класс который отвечает за сохранение данных из сервера, которых нет в базе данных
@ExperimentalPagingApi
class HeroRemoteMediator @Inject constructor(
    private val borutoApi: BorutoApi,
    private val borutoDatabase: BorutoDatabase
) : RemoteMediator<Int, Hero>() {

    private val heroDao = borutoDatabase.heroDao()
    private val heroRemoteKeysDao = borutoDatabase.heroRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = heroRemoteKeysDao.getRemoteKeys(id = 1)?.lastUpdated ?: 0L
        val cacheTimeout = 5

        val diffInMinutes = (currentTime - lastUpdated) / 1000 / 60
        return if (diffInMinutes.toInt() <= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Hero>): MediatorResult {
        try {

            val page: Int = when (loadType) {
                LoadType.REFRESH -> {
                    //для обновления текущей страницы ?
                    val remoteKeys = getRemoteKeysClosestToCurrentPosition(state = state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    //для просмотра предыдущей страницы
                    val remoteKeys = getRemoteKeysForFirstItem(state = state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    //для обнолвения следующей страницы
                    val remoteKeys = getRemoteKeysForLastItem(state = state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }

            }

            val response = borutoApi.getAllHeroes(page = page)
            if (response.heroes.isNotEmpty()) {
                //позволяет выполнить сразу несколько операций над БД в одной транзакции
                borutoDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        heroDao.deleteAllHeroes()
                        heroRemoteKeysDao.deleteAllRemoteKeys()
                    }
                    val prevPage = response.prevPage
                    val nextPage = response.nextPage
                    val keys = response.heroes.map { hero ->
                        HeroRemoteKeys(
                            id = hero.id,
                            prevPage = prevPage,
                            nextPage = nextPage,
                            lastUpdated = response.lastUpdated
                        )
                    }
                    heroRemoteKeysDao.addAllRemoteKeys(heroRemoteKeys = keys)
                    heroDao.addHeroes(heroes = response.heroes)
                }
                //необходимо ли надо будет еще загружать страницы, смотрим, если следующей страницы нет, то значит не будет больше ниче выкачивать
            }
            return MediatorResult.Success(endOfPaginationReached = response.nextPage == null)
        } catch (ex: Exception) {
            return MediatorResult.Error(ex)
        }
    }

    //смотрим последнего в текущей сохраненной странице и по нему определяем какая страница будет следущей
    private suspend fun getRemoteKeysForLastItem(
        state: PagingState<Int, Hero>
    ): HeroRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { hero ->
            heroRemoteKeysDao.getRemoteKeys(hero.id)
        }
    }

    //смотрим по первому значению в странице и определяем какая страница будет предыдущей
    private suspend fun getRemoteKeysForFirstItem(
        state: PagingState<Int, Hero>
    ): HeroRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { hero ->
            heroRemoteKeysDao.getRemoteKeys(hero.id)
        }
    }

    //получение ближайшего значения к текущей позиции состояния
    private suspend fun getRemoteKeysClosestToCurrentPosition(
        state: PagingState<Int, Hero>
    ): HeroRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                heroRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }
}

