package com.pixabay.challenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.pixabay.challenge.data.datastore.local.ImageRepositoryDao
import com.pixabay.challenge.data.datastore.remote.ImageApiService
import com.pixabay.challenge.data.interfaces.LocalImageDataSource
import com.pixabay.challenge.data.interfaces.LocalImageDataSourceImpl
import com.pixabay.challenge.data.interfaces.RemoteImageDataSource
import com.pixabay.challenge.data.interfaces.RemoteImageDataSourceImpl
import com.pixabay.challenge.data.mapper.ImageDbModelToDomainModelMapper
import com.pixabay.challenge.data.mapper.ImageNetworkModelToDbModelMapper
import com.pixabay.challenge.data.mapper.ImageNetworkModelToDomainModelMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideLocalImageDataSource(
        imageRepositoryDao: ImageRepositoryDao,
        imageDbModelToDomainModelMapper: ImageDbModelToDomainModelMapper,
    ): LocalImageDataSource {
        return LocalImageDataSourceImpl(
            imageRepositoryDao = imageRepositoryDao,
            imageDbModelToDomainModelMapper = imageDbModelToDomainModelMapper,
        )
    }

    @Provides
    @Singleton
    fun provideRemoteImageDataSource(
        imageApiService: ImageApiService,
        localImageDataSource: LocalImageDataSource,
        imageNetworkModelToDomainModelMapper: ImageNetworkModelToDomainModelMapper,
        imageNetworkModelToDbModelMapper: ImageNetworkModelToDbModelMapper
    ): RemoteImageDataSource {
        return RemoteImageDataSourceImpl(
            imageApiService = imageApiService,
            localImageDataSource = localImageDataSource,
            imageNetworkModelToDomainModelMapper = imageNetworkModelToDomainModelMapper,
            imageNetworkModelToDbModelMapper = imageNetworkModelToDbModelMapper
        )
    }
}

