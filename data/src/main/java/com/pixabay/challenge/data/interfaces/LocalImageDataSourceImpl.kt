package com.pixabay.challenge.data.interfaces

import com.pixabay.challenge.data.datastore.local.ImageLocalModel
import com.pixabay.challenge.data.datastore.local.ImageRepositoryDao
import com.pixabay.challenge.data.mapper.ImageDbModelToDomainModelMapper
import com.pixabay.challenge.domain.model.ImageDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalImageDataSourceImpl(
    private val imageRepositoryDao: ImageRepositoryDao,
    private val imageDbModelToDomainModelMapper: ImageDbModelToDomainModelMapper
) : LocalImageDataSource {

    override fun fetchImagesWithTimestamp(query: String): Flow<Pair<List<ImageDomainModel>?, Long>> {
        return imageRepositoryDao.fetchImagesByQueryWithTimestamps(query)
            .map { imagesWithTimestamps ->
                val domainModels = imagesWithTimestamps.map(imageDbModelToDomainModelMapper::toDomain)
                val timestamp = imagesWithTimestamps.maxOfOrNull { it.createdAt } ?: 0L
                domainModels to timestamp
            }
    }

    override fun fetchImages(query: String): Flow<List<ImageDomainModel>> {
        return imageRepositoryDao.fetchImagesByQuery(query)
            .map { images ->
                images.map(imageDbModelToDomainModelMapper::toDomain)
            }
    }

    override suspend fun removeImagesByQuery(query: String) {
        imageRepositoryDao.removeImagesByQuery(query)
    }

    override suspend fun saveImages(images: List<ImageLocalModel>?) {
        images?.let {
            imageRepositoryDao.saveImages(it)
        }
    }

    override suspend fun getLastUpdateTime(): Long {
        return imageRepositoryDao.getLastUpdateTime() ?: 0L
    }
}
