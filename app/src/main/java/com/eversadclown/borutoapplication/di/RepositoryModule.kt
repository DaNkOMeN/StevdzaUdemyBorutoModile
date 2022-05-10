package com.eversadclown.borutoapplication.di

import android.content.Context
import com.eversadclown.borutoapplication.data.repository.DataStoreOperationsImpl
import com.eversadclown.borutoapplication.data.repository.Repository
import com.eversadclown.borutoapplication.domain.repository.DataStoreOperations
import com.eversadclown.borutoapplication.domain.use_cases.UseCases
import com.eversadclown.borutoapplication.domain.use_cases.get_all_heroes.GetAllHeroesUseCase
import com.eversadclown.borutoapplication.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.eversadclown.borutoapplication.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//работа с хранением переменных самого приложения, например сохранение флага просмотренного велком-экрана
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    //предоставить доступ к объекту, которых позволяет взаимодействовать с данными приложения (флагами, созраненной цветовой теме
    @Provides
    @Singleton
    fun provideDataStoreOperations(
        @ApplicationContext context: Context
    ): DataStoreOperations {
        return DataStoreOperationsImpl(context = context)
    }

    //предоставить созможность
    @Provides
    @Singleton
    fun provideUseCases(
        repository: Repository
    ) : UseCases {
        return UseCases(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(repository = repository),
            readOnBoardingUseCase = ReadOnBoardingUseCase(repository = repository),
            getAllHeroesUseCase = GetAllHeroesUseCase(repository = repository)
        )
    }

}
