package payback.pixabay.challenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import payback.pixabay.challenge.data.datastore.local.ImageRepositoryDao
import payback.pixabay.challenge.data.datastore.remote.ImageApiService
import payback.pixabay.challenge.data.mapper.ImageNetworkModelToDomainModelMapper
import payback.pixabay.challenge.data.mapper.ImageDbModelToDomainModelMapper
import payback.pixabay.challenge.data.mapper.ImageNetworkModelToDbModelMapper
import payback.pixabay.challenge.data.repository.ImagesDataRepository
import payback.pixabay.challenge.domain.repository.ImageRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideImagesDataRepository(
        imageApiService: ImageApiService,
        imageDetailsDao: ImageRepositoryDao,
        imageNetworkModelToDomainModelMapper: ImageNetworkModelToDomainModelMapper,
        imageNetworkModelToDbModelMapper: ImageNetworkModelToDbModelMapper,
        imageDbModelToDomainModelMapper: ImageDbModelToDomainModelMapper
    ): ImageRepository {
        return ImagesDataRepository(
            imageApiService,
            imageDetailsDao,
            imageNetworkModelToDomainModelMapper,
            imageNetworkModelToDbModelMapper,
            imageDbModelToDomainModelMapper
        )
    }
}
