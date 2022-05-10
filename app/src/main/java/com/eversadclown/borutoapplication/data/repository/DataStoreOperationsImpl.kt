package com.eversadclown.borutoapplication.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.eversadclown.borutoapplication.domain.repository.DataStoreOperations
import com.eversadclown.borutoapplication.util.Constants.PREFERENCES_KEY
import com.eversadclown.borutoapplication.util.Constants.PREFERENCES_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import java.io.IOException

//задаем название для нашего датастора для всего приложения, переопределяя переменную
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)
//класс для сохранения данных в кеше приложения (флаги, темы или что то другое)
class DataStoreOperationsImpl(context: Context) : DataStoreOperations {

    //название буленовского сохраняемого в кеше значения
    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(name = PREFERENCES_KEY)
    }

    private val dataStore = context.dataStore

    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onBoardingKey] = completed
        }
    }

    override fun readOnBoardingState(): Flow<Boolean> =
        dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val onBoardingState = preferences[PreferencesKey.onBoardingKey] ?: false
            onBoardingState
        }

}