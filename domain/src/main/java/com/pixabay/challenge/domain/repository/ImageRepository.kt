package com.pixabay.challenge.domain.repository

import com.pixabay.challenge.domain.model.ImageDomainModel

interface ImageRepository {
    suspend fun fetchImages(query: String): List<ImageDomainModel>
}
