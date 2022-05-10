package com.eversadclown.borutoapplication.di

import android.content.Context
import androidx.room.Room
import com.eversadclown.borutoapplication.data.local.BorutoDatabase
import com.eversadclown.borutoapplication.util.Constants.BORUTO_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//модуль с базой данных, в которую добавляются все закачанные персонажи
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            BorutoDatabase::class.java,
            BORUTO_DATABASE
        ).build()


}