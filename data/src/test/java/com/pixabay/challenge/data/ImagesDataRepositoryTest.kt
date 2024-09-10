package com.pixabay.challenge.data

import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import com.pixabay.challenge.data.datastore.local.ImageLocalModel
import com.pixabay.challenge.data.interfaces.LocalImageDataSource
import com.pixabay.challenge.data.interfaces.RemoteImageDataSource
import com.pixabay.challenge.data.mapper.ImageNetworkModelToDbModelMapper
import com.pixabay.challenge.data.mapper.MapperInput
import com.pixabay.challenge.data.model.ImageRemoteModel
import com.pixabay.challenge.data.repository.ImagesDataRepository
import com.pixabay.challenge.domain.model.ImageDomainModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import org.mockito.Mockito.verify

private const val FETCH_INTERVAL_IN_SECONDS: Int = 24 * 60 * 60

@RunWith(MockitoJUnitRunner.Silent::class)
class ImagesDataRepositoryTest {

    private val searchQuery = "blue"
    private val currentTimeInSeconds = System.currentTimeMillis() / 1000L

    private val sampleImageDetailFromApi = ImageRemoteModel(
        id = 1,
        pageURL = "https://example.com",
        type = "photo",
        tags = "nature, landscape",
        previewURL = "https://example.com/preview",
        webFormatURL = "https://example.com/webformat",
        largeImageURL = "https://example.com/large",
        downloads = 50,
        likes = 10,
        comments = 5,
        userId = 123,
        user = "test1",
        userImageURL = "https://example.com/user"
    )

    private val sampleImageDetailFromDomain = ImageDomainModel(
        id = 1,
        pageURL = "https://example.com",
        type = "photo",
        tags = "nature, landscape",
        previewURL = "https://example.com/preview",
        webFormatURL = "https://example.com/webformat",
        largeImageURL = "https://example.com/large",
        downloads = 50,
        likes = 10,
        comments = 5,
        userId = 123,
        user = "test2",
        userImageURL = "https://example.com/user"
    )

    private val sampleImageDetailFromDb = ImageLocalModel(
        id = 1,
        pageURL = "https://example.com",
        type = "photo",
        tags = "nature, landscape",
        previewURL = "https://example.com/preview",
        webFormatURL = "https://example.com/webformat",
        largeImageURL = "https://example.com/large",
        downloads = 50,
        likes = 10,
        comments = 5,
        userId = 123,
        user = "test3",
        userImageURL = "https://example.com/user",
        searchQuery = "red",
        createdAt = currentTimeInSeconds
    )

    @Mock
    lateinit var networkToDbMapper: ImageNetworkModelToDbModelMapper

    @Mock
    lateinit var localImageDataSource: LocalImageDataSource

    @Mock
    lateinit var remoteImageDataSource: RemoteImageDataSource

    private lateinit var repository: ImagesDataRepository

    @Before
    fun setup() {
        repository = ImagesDataRepository(
            localImageDataSource = localImageDataSource,
            remoteImageDataSource = remoteImageDataSource,
        )
    }

    @Test
    fun fetchImages_returnsLocalData_whenItIsValid() {
        runBlocking {
            val expectedResult = listOf(sampleImageDetailFromDomain)
            val fetchTimestamp = currentTimeInSeconds - 100L

            given(localImageDataSource.fetchImagesWithTimestamp(searchQuery))
                .willReturn(listOf(sampleImageDetailFromDomain) to fetchTimestamp)

            val actualResult = repository.fetchImages(searchQuery)

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun fetchImages_refreshesDataFromRemote_whenLocalDataIsStaleAndUpdatesDatabase() {
        runBlocking {
            val staleTimestamp = currentTimeInSeconds - FETCH_INTERVAL_IN_SECONDS - 100L
            val expectedResult = flowOf(Result.success(listOf(sampleImageDetailFromDomain)))

            given(localImageDataSource.fetchImagesWithTimestamp(searchQuery))
                .willReturn(listOf(sampleImageDetailFromDomain) to staleTimestamp)

            given(remoteImageDataSource.fetchImages(searchQuery))
                .willReturn(expectedResult)

            given(
                networkToDbMapper.toDatabase(
                    MapperInput(
                        apiImageDetails = sampleImageDetailFromApi,
                        searchQuery = searchQuery,
                        createdAt = currentTimeInSeconds
                    )
                )
            ).willReturn(sampleImageDetailFromDb)

            val actualResult = repository.fetchImages(searchQuery)

            assertEquals(expectedResult.firstOrNull()?.getOrNull(), actualResult)
            verify(localImageDataSource).removeImagesByQuery(searchQuery)
        }
    }

    @Test
    fun fetchImages_fetchesDataFromRemote_whenNoLocalDataIsFound() {
        runBlocking {
            val expectedResult = flowOf(Result.success(listOf(sampleImageDetailFromDomain)))

            given(localImageDataSource.fetchImagesWithTimestamp(searchQuery))
                .willReturn(emptyList<ImageDomainModel>() to 0L)

            given(remoteImageDataSource.fetchImages(searchQuery))
                .willReturn(expectedResult)

            val actualResult = repository.fetchImages(searchQuery)

            assertEquals(expectedResult.firstOrNull()?.getOrNull(), actualResult)
        }
    }
}
