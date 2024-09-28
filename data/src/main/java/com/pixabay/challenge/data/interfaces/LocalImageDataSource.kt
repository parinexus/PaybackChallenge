package com.pixabay.challenge.data.interfaces

import com.pixabay.challenge.data.datastore.local.ImageLocalModel
import com.pixabay.challenge.domain.model.ImageDomainModel
import kotlinx.coroutines.flow.Flow

interface LocalImageDataSource {
    fun fetchImagesWithTimestamp(query: String): Flow<Pair<List<ImageDomainModel>?, Long>>
    fun fetchImages(query: String): Flow<List<ImageDomainModel>>
    suspend fun removeImagesByQuery(query: String)
    suspend fun saveImages(images: List<ImageLocalModel>?)
    suspend fun getLastUpdateTime(): Long
}
