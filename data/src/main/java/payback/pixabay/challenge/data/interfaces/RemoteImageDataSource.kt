package payback.pixabay.challenge.data.interfaces

import payback.pixabay.challenge.domain.model.ImageDomainModel

interface RemoteImageDataSource {
    suspend fun fetchImages(query: String): List<ImageDomainModel>
}