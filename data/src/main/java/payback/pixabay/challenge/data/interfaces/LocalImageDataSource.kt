package payback.pixabay.challenge.data.interfaces

import payback.pixabay.challenge.data.datastore.local.ImageLocalModel
import payback.pixabay.challenge.domain.model.ImageDomainModel

interface LocalImageDataSource {
    suspend fun fetchImagesWithTimestamp(query: String): Pair<List<ImageDomainModel>, Long>
    suspend fun fetchImages(query: String): List<ImageDomainModel>
    suspend fun removeImagesByQuery(query: String)
    suspend fun saveImages(images: List<ImageLocalModel>?)
}
