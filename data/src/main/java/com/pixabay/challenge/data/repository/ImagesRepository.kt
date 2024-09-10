package com.pixabay.challenge.data.repository

import com.pixabay.challenge.data.interfaces.LocalImageDataSource
import com.pixabay.challenge.data.interfaces.RemoteImageDataSource
import com.pixabay.challenge.domain.model.ImageDomainModel
import com.pixabay.challenge.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

private const val FETCH_INTERVAL_IN_SECONDS: Int = 24 * 60 * 60

class ImagesDataRepository @Inject constructor(
    private val localImageDataSource: LocalImageDataSource,
    private val remoteImageDataSource: RemoteImageDataSource,
) : ImageRepository {

    override suspend fun fetchImages(query: String): List<ImageDomainModel> {
        val (localData, localTimestamp) = localImageDataSource.fetchImagesWithTimestamp(query)

        return when {
            localData.isEmpty() -> {
                fetchAndPersistData(query).firstOrNull()?.getOrNull() ?: emptyList()
            }

            isRefreshNeeded(localTimestamp) -> {
                localImageDataSource.removeImagesByQuery(query)
                fetchAndPersistData(query).firstOrNull()?.getOrNull() ?: emptyList()
            }

            else -> {
                localData
            }
        }
    }

    private suspend fun fetchAndPersistData(query: String): Flow<Result<List<ImageDomainModel>>> {
        return remoteImageDataSource.fetchImages(query)
    }

    private fun isRefreshNeeded(lastFetchTimeInSeconds: Long): Boolean {
        return getCurrentTimeInSeconds() - lastFetchTimeInSeconds >= FETCH_INTERVAL_IN_SECONDS
    }

    private fun getCurrentTimeInSeconds(): Long = System.currentTimeMillis() / 1000L
}

