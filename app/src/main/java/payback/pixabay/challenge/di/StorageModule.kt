package payback.pixabay.challenge.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import payback.pixabay.challenge.data.datastore.local.ImageRepositoryDao
import payback.pixabay.challenge.db.AppStorage

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {
    @Provides
    fun providesAppStorage(@ApplicationContext context: Context) = AppStorage.create(context)

    @Provides
    fun providesImageRepositoryDao(appStorage: AppStorage): ImageRepositoryDao = appStorage.imageDao()
}
