package payback.pixabay.challenge.data.repository

import payback.pixabay.challenge.data.datastore.local.ImageRepositoryDao
import payback.pixabay.challenge.data.datastore.remote.ImageApiService
import payback.pixabay.challenge.data.mapper.ImageNetworkModelToDomainModelMapper
import payback.pixabay.challenge.data.mapper.ImageDbModelToDomainModelMapper
import payback.pixabay.challenge.data.mapper.ImageNetworkModelToDbModelMapper
import payback.pixabay.challenge.data.mapper.MapperInput
import payback.pixabay.challenge.domain.model.ImageDomainModel
import payback.pixabay.challenge.domain.repository.ImageRepository
import javax.inject.Inject

private const val FETCH_INTERVAL_IN_SECONDS: Int = (24 * 60 * 60)

class ImagesDataRepository @Inject constructor(
    private val imageApiService: ImageApiService,
    private val imageRepositoryDao: ImageRepositoryDao,
    private val imageNetworkModelToDomainModelMapper: ImageNetworkModelToDomainModelMapper,
    private val imageNetworkModelToDbModelMapper: ImageNetworkModelToDbModelMapper,
    private val imageDbModelToDomainModelMapper: ImageDbModelToDomainModelMapper
) : ImageRepository {
    override suspend fun fetchImages(query: String): List<ImageDomainModel> {
        val localData = imageRepositoryDao.fetchImagesByQuery(query)
        return when {
            localData.isNullOrEmpty() -> {
                fetchAndPersistData(query)
            }
            isRefreshNeeded(localData[0].createdAt) -> {
                imageRepositoryDao.removeImagesByQuery(query)
                fetchAndPersistData(query)
            }
            else -> {
                localData.map { data -> imageDbModelToDomainModelMapper.toDomain(data) }
            }
        }
    }

    private suspend fun fetchAndPersistData(query: String): List<ImageDomainModel> {
        val imagesApiResponse = imageApiService.fetchImages(query = query)
        imagesApiResponse.images?.map { apiImageDetail ->
            imageNetworkModelToDbModelMapper.toDatabase(
                MapperInput(apiImageDetail, query, getCurrentTimeInSeconds())
            )
        }?.let { images -> imageRepositoryDao.saveImages(images) }

        return imagesApiResponse.images?.map { imageDetail ->
            imageNetworkModelToDomainModelMapper.toDomain(imageDetail)
        } ?: emptyList()
    }

    private fun getCurrentTimeInSeconds() = System.currentTimeMillis() / 1000L

    private fun isRefreshNeeded(lastFetchTimeInSeconds: Long): Boolean {
        return getCurrentTimeInSeconds() - lastFetchTimeInSeconds >= FETCH_INTERVAL_IN_SECONDS
    }
}
