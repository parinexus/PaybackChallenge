package payback.pixabay.challenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import payback.pixabay.challenge.data.mapper.ImageNetworkModelToDomainModelMapper
import payback.pixabay.challenge.data.mapper.ImageDbModelToDomainModelMapper
import payback.pixabay.challenge.data.mapper.ImageNetworkModelToDbModelMapper
import payback.pixabay.challenge.mapper.ImageDomainModelToUiModelMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {
    @Singleton
    @Provides
    fun provideImageNetworkModelToDomainModelMapper(): ImageNetworkModelToDomainModelMapper =
        ImageNetworkModelToDomainModelMapper()

    @Singleton
    @Provides
    fun provideImageDomainModelToPresentationModelMapper(): ImageDomainModelToUiModelMapper =
        ImageDomainModelToUiModelMapper()

    @Singleton
    @Provides
    fun provideImageDbModelToDomainModelMapper(): ImageDbModelToDomainModelMapper =
        ImageDbModelToDomainModelMapper()

    @Singleton
    @Provides
    fun provideImageNetworkModelToDbModelMapper(): ImageNetworkModelToDbModelMapper =
        ImageNetworkModelToDbModelMapper()
}
