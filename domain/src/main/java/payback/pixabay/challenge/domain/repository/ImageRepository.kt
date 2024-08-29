package payback.pixabay.challenge.domain.repository

import payback.pixabay.challenge.domain.model.ImageDomainModel

interface ImageRepository {
    suspend fun fetchImages(query: String): List<ImageDomainModel>
}
