package com.pixabay.challenge.data.interfaces

import com.pixabay.challenge.data.datastore.remote.ImageApiService
import com.pixabay.challenge.data.mapper.ImageNetworkModelToDbModelMapper
import com.pixabay.challenge.data.mapper.ImageNetworkModelToDomainModelMapper
import com.pixabay.challenge.data.mapper.MapperInput
import com.pixabay.challenge.data.model.ImagesResponse
import com.pixabay.challenge.domain.model.ImageDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteImageDataSourceImpl @Inject constructor(
    private val imageApiService: ImageApiService,
    private val localImageDataSource: LocalImageDataSource,
    private val imageNetworkModelToDomainModelMapper: ImageNetworkModelToDomainModelMapper,
    private val imageNetworkModelToDbModelMapper: ImageNetworkModelToDbModelMapper
) : RemoteImageDataSource {

    override suspend fun fetchImages(query: String): Flow<Result<List<ImageDomainModel>>> = flow {
        val imagesApiResponse = imageApiService.fetchImages(query = query)

        saveImagesWithinDataBase(imagesApiResponse, query)

        val domainModels = imagesApiResponse.images?.map { imageDetail ->
            imageNetworkModelToDomainModelMapper.toDomain(imageDetail)
        } ?: emptyList()

        emit(Result.success(domainModels))
    }.catch { exception ->
        emit(Result.failure(exception))
    }

    suspend fun saveImagesWithinDataBase(
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

    fun getCurrentTimeInSeconds(): Long = System.currentTimeMillis() / 1000L
}
