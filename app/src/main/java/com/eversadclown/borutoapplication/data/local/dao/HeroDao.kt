package com.eversadclown.borutoapplication.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.eversadclown.borutoapplication.domain.model.Hero


//интерфейс, инкапсулирующий обращения к БД для таблицы с персонажами
@Dao
interface HeroDao {

    @Query("SELECT * FROM hero_table ORDER BY id ASC")
    fun getAllHeroes(): PagingSource<Int, Hero>

    @Query("SELECT * FROM hero_table WHERE id=:heroId")
    fun getSelectedHero(heroId: Int): Hero

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHeroes(heroes: List<Hero>)

    @Query("DELETE FROM hero_table")
    suspend fun deleteAllHeroes()

}