package com.pixabay.challenge.data.interfaces

import com.pixabay.challenge.domain.model.ImageDomainModel
import kotlinx.coroutines.flow.Flow

interface RemoteImageDataSource {
    suspend fun fetchImages(query: String): Flow<Result<List<ImageDomainModel>>>
}