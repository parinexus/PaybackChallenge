package com.pixabay.challenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.pixabay.challenge.data.mapper.ImageNetworkModelToDomainModelMapper
import com.pixabay.challenge.data.mapper.ImageDbModelToDomainModelMapper
import com.pixabay.challenge.data.mapper.ImageNetworkModelToDbModelMapper
import com.pixabay.challenge.mapper.ImageDomainModelToUiModelMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {
    @Provides
    fun provideImageNetworkModelToDomainModelMapper(): ImageNetworkModelToDomainModelMapper =
        ImageNetworkModelToDomainModelMapper()

    @Provides
    fun provideImageDomainModelToUiModelMapper(): ImageDomainModelToUiModelMapper =
        ImageDomainModelToUiModelMapper()

    @Provides
    fun provideImageDbModelToDomainModelMapper(): ImageDbModelToDomainModelMapper =
        ImageDbModelToDomainModelMapper()

    @Provides
    fun provideImageNetworkModelToDbModelMapper(): ImageNetworkModelToDbModelMapper =
        ImageNetworkModelToDbModelMapper()
}
