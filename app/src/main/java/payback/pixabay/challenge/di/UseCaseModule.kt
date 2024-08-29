package payback.pixabay.challenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import payback.pixabay.challenge.domain.repository.ImageRepository
import payback.pixabay.challenge.domain.usecase.ImagesUseCase

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideImagesUseCase(imagesRepository: ImageRepository) =
        ImagesUseCase(imagesRepository)
}
