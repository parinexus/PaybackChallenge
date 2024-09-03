package payback.pixabay.challenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import payback.pixabay.challenge.data.datastore.local.ImageRepositoryDao
import payback.pixabay.challenge.data.datastore.remote.ImageApiService
import payback.pixabay.challenge.data.interfaces.LocalImageDataSource
import payback.pixabay.challenge.data.interfaces.LocalImageDataSourceImpl
import payback.pixabay.challenge.data.interfaces.RemoteImageDataSource
import payback.pixabay.challenge.data.interfaces.RemoteImageDataSourceImpl
import payback.pixabay.challenge.data.mapper.ImageDbModelToDomainModelMapper
import payback.pixabay.challenge.data.mapper.ImageNetworkModelToDbModelMapper
import payback.pixabay.challenge.data.mapper.ImageNetworkModelToDomainModelMapper
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

