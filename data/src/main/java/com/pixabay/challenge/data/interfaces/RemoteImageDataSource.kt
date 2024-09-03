package com.pixabay.challenge.data.interfaces

import com.pixabay.challenge.domain.model.ImageDomainModel

interface RemoteImageDataSource {
    suspend fun fetchImages(query: String): List<ImageDomainModel>
}