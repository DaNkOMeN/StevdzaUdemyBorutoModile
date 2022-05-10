package com.eversadclown.borutoapplication.data.local

import androidx.room.TypeConverter

//конвертер чтобы из страки получить список строк
class DatabaseConverter {
    private val separator = ","

    @TypeConverter
    fun convertListToString(list: List<String>): String {
        return list.joinToString(separator = this.separator)
    }

    @TypeConverter
    fun convertStringToList(string: String): List<String> {
        return string.split(separator)
    }
}