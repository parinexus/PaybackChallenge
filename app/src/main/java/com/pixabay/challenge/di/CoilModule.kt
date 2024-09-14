package com.pixabay.challenge.di

import android.content.Context
import coil.ImageLoader
import com.pixabay.challenge.CoilImageLoaderDelegate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoilModule {

    @Provides
    @Singleton
    fun provideCoilImageLoaderDelegate(
        @ApplicationContext context: Context
    ): CoilImageLoaderDelegate {
        return CoilImageLoaderDelegate(context)
    }

    @Provides
    @Singleton
    fun provideImageLoader(
        coilImageLoaderDelegate: CoilImageLoaderDelegate
    ): ImageLoader {
        return coilImageLoaderDelegate.buildImageLoader()
    }
}
