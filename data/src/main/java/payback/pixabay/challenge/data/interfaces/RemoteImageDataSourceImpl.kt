package payback.pixabay.challenge.data.interfaces

import payback.pixabay.challenge.data.datastore.local.ImageRepositoryDao
import payback.pixabay.challenge.data.datastore.remote.ImageApiService
import payback.pixabay.challenge.data.mapper.ImageNetworkModelToDbModelMapper
import payback.pixabay.challenge.data.mapper.ImageNetworkModelToDomainModelMapper
import payback.pixabay.challenge.data.mapper.MapperInput
import payback.pixabay.challenge.data.model.ImagesResponse
import payback.pixabay.challenge.domain.model.ImageDomainModel
import javax.inject.Inject

class RemoteImageDataSourceImpl @Inject constructor(
    private val imageApiService: ImageApiService,
    private val localImageDataSource: LocalImageDataSource,
    private val imageNetworkModelToDomainModelMapper: ImageNetworkModelToDomainModelMapper,
    private val imageNetworkModelToDbModelMapper: ImageNetworkModelToDbModelMapper
) : RemoteImageDataSource {

    override suspend fun fetchImages(query: String): List<ImageDomainModel> {
        val imagesApiResponse = imageApiService.fetchImages(query = query)

        saveImagesWithinDataBase(imagesApiResponse, query)

        return imagesApiResponse.images?.map { imageDetail ->
            imageNetworkModelToDomainModelMapper.toDomain(imageDetail)
        } ?: emptyList()
    }

    private suspend fun saveImagesWithinDataBase(
        imagesApiResponse: ImagesResponse,
        query: String
    ) {
        val dbModels = imagesApiResponse.images?.map { apiImageDetail ->
            imageNetworkModelToDbModelMapper.toDatabase(
                MapperInput(apiImageDetail, query, getCurrentTimeInSeconds())
            )
        } ?: emptyList()

        if (dbModels.isNotEmpty()) {
            localImageDataSource.saveImages(dbModels)
        }
    }

    private fun getCurrentTimeInSeconds(): Long = System.currentTimeMillis() / 1000L
}
