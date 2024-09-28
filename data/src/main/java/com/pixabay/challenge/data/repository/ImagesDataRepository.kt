package com.pixabay.challenge.data.repository

import com.pixabay.challenge.common.ResultModel
import com.pixabay.challenge.data.interfaces.LocalImageDataSource
import com.pixabay.challenge.data.interfaces.RemoteImageDataSource
import com.pixabay.challenge.domain.model.ImageDomainModel
import com.pixabay.challenge.domain.repository.ImageRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

private const val FETCH_INTERVAL_IN_SECONDS: Int = 24 * 60 * 60

class ImagesDataRepository(
    private val localImageDataSource: LocalImageDataSource,
    private val remoteImageDataSource: RemoteImageDataSource,
) : ImageRepository {

    override suspend fun fetchImages(query: String): ResultModel<List<ImageDomainModel>> {
        val localDataFlow = localImageDataSource.fetchImagesWithTimestamp(query)

        return localDataFlow.firstOrNull()?.let { (localData, localTimestamp) ->
            val nonNullLocalData = localData ?: emptyList()

            if (nonNullLocalData.isEmpty() || isRefreshNeeded(localTimestamp)) {
                fetchAndPersistData(query)
            } else {
                ResultModel.Success(nonNullLocalData)
            }
        } ?: fetchAndPersistData(query)
    }

    private suspend fun fetchAndPersistData(query: String): ResultModel<List<ImageDomainModel>> {
        return try {
            return when (val result = remoteImageDataSource.fetchImages(query).first()) {
                is ResultModel.Success -> {
                    localImageDataSource.saveImages(result.data)
                    ResultModel.Success(localImageDataSource.fetchImages(query).first())
                }
                is ResultModel.Error -> {
                    ResultModel.Error(message = result.message, data = null)
                }
            }
        } catch (e: Exception) {
            ResultModel.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    private fun isRefreshNeeded(lastFetchTimeInSeconds: Long): Boolean {
        return getCurrentTimeInSeconds() - lastFetchTimeInSeconds >= FETCH_INTERVAL_IN_SECONDS
    }

    private fun getCurrentTimeInSeconds(): Long = System.currentTimeMillis() / 1000L
}

