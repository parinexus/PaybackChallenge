package payback.pixabay.challenge.data.interfaces

import payback.pixabay.challenge.data.datastore.local.ImageLocalModel
import payback.pixabay.challenge.data.datastore.local.ImageRepositoryDao
import payback.pixabay.challenge.data.mapper.ImageDbModelToDomainModelMapper
import payback.pixabay.challenge.domain.model.ImageDomainModel
import javax.inject.Inject

class LocalImageDataSourceImpl @Inject constructor(
    private val imageRepositoryDao: ImageRepositoryDao,
    private val imageDbModelToDomainModelMapper: ImageDbModelToDomainModelMapper
) : LocalImageDataSource {

    override suspend fun fetchImagesWithTimestamp(query: String): Pair<List<ImageDomainModel>, Long> {
        val imagesWithTimestamps = imageRepositoryDao.fetchImagesByQueryWithTimestamps(query) ?: emptyList()
        val domainModels = imagesWithTimestamps.map(imageDbModelToDomainModelMapper::toDomain)
        val timestamp = imagesWithTimestamps.maxOfOrNull { it.createdAt } ?: 0L
        return domainModels to timestamp
    }

    override suspend fun fetchImages(query: String): List<ImageDomainModel> {
        return imageRepositoryDao.fetchImagesByQuery(query)
            ?.map(imageDbModelToDomainModelMapper::toDomain)
            ?: emptyList()
    }

    override suspend fun removeImagesByQuery(query: String) {
        imageRepositoryDao.removeImagesByQuery(query)
    }

    override suspend fun saveImages(images: List<ImageLocalModel>?) {
        images?.let {
            imageRepositoryDao.saveImages(it)
        }
    }
}
