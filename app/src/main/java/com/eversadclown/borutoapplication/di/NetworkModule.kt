@file:OptIn(ExperimentalSerializationApi::class)

package com.eversadclown.borutoapplication.di

import com.eversadclown.borutoapplication.data.local.BorutoDatabase
import com.eversadclown.borutoapplication.data.remote.BorutoApi
import com.eversadclown.borutoapplication.data.repository.RemoteDataSourceImpl
import com.eversadclown.borutoapplication.domain.repository.RemoteDataSource
import com.eversadclown.borutoapplication.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    //созздание конфигурации для обращения к серверной части приложения
    @Singleton
    @Provides
    fun provideRetrofitInstance(httpClient: OkHttpClient): Retrofit {
        val contentType = MediaType.get("application/json")
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    //создание объекта, для доступа к серверной части приложения
    @Provides
    @Singleton
    fun provideBorutoApi(retrofit: Retrofit): BorutoApi {
        return retrofit.create(BorutoApi::class.java)
    }

    //создание объекта, который обеспечивает доступ к данным БД и сервера
    @Provides
    @Singleton
    fun provideRemoteDataSource(
        borutoApi: BorutoApi,
        borutoDatabase: BorutoDatabase
    ): RemoteDataSource =
        RemoteDataSourceImpl(borutoDatabase = borutoDatabase, borutoApi = borutoApi)


}