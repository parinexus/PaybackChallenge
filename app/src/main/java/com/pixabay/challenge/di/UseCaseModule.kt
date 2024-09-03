package com.pixabay.challenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.pixabay.challenge.domain.repository.ImageRepository
import com.pixabay.challenge.domain.usecase.ImagesUseCase

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideImagesUseCase(imagesRepository: ImageRepository) =
        ImagesUseCase(imagesRepository)
}
