package payback.pixabay.challenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import payback.pixabay.challenge.data.interfaces.LocalImageDataSource
import payback.pixabay.challenge.data.interfaces.RemoteImageDataSource
import payback.pixabay.challenge.data.repository.ImagesDataRepository
import payback.pixabay.challenge.domain.repository.ImageRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideImagesDataRepository(
        localImageDataSource: LocalImageDataSource,
        remoteImageDataSource: RemoteImageDataSource,
    ): ImageRepository {
        return ImagesDataRepository(
            localImageDataSource,
            remoteImageDataSource,
        )
    }
}
