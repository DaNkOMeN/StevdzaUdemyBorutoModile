package com.eversadclown.borutoapplication.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eversadclown.borutoapplication.util.Constants.HERO_DATABASE_TABLE
import kotlinx.serialization.Serializable


//класс персонажа, хранится в одной из таблиц базы данных
@Serializable
@Entity(tableName = HERO_DATABASE_TABLE)
data class Hero(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val image: String,
    val about: String,
    val rating: Double,
    val power: Int,
    val month: String,
    val day: String,
    val family: List<String>,
    val abilities: List<String>,
    val natureTypes: List<String>
)
