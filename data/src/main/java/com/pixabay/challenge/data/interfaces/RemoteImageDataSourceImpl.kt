package com.pixabay.challenge.data.interfaces

import com.pixabay.challenge.common.ResultModel
import com.pixabay.challenge.data.datastore.local.ImageLocalModel
import com.pixabay.challenge.data.datastore.remote.ImageApiService
import com.pixabay.challenge.data.mapper.ImageNetworkModelToDbModelMapper
import com.pixabay.challenge.data.mapper.MapperInput
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteImageDataSourceImpl @Inject constructor(
    private val imageApiService: ImageApiService,
    private val imageNetworkModelToDbModelMapper: ImageNetworkModelToDbModelMapper
) : RemoteImageDataSource {

    override fun fetchImages(query: String) = flow<ResultModel<List<ImageLocalModel>>> {
        val imagesApiResponse = imageApiService.fetchImages(query = query)
        val dbModels = imagesApiResponse.images.map { apiImageDetail ->
            imageNetworkModelToDbModelMapper.toDatabase(
                MapperInput(apiImageDetail, query, getCurrentTimeInSeconds())
            )
        } ?: emptyList()
        emit(ResultModel.Success(dbModels))
    }.catch { exception ->
        emit(ResultModel.Error(message = exception.message,data = null))
    }

    fun getCurrentTimeInSeconds(): Long = System.currentTimeMillis() / 1000L
}
