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
        user = "johndoe",
        userImageURL = "https://example.com/user"
    )
    private val sampleImageDetailInDomain = ImageDomainModel(
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
        user = "johndoe",
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
        user = "johndoe",
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
    fun `When fetchImages is called and local data is available and valid Then it returns list of ImageDataModelInDomain from local DB`() {
        runBlocking {
            val expectedResult = listOf(sampleImageDetailInDomain)
            val fetchTimestamp = currentTimeInSeconds - 100L // Timestamp within valid range

            given(localImageDataSource.fetchImagesWithTimestamp(searchQuery))
                .willReturn(listOf(sampleImageDetailInDomain) to fetchTimestamp)

            val actualResult = repository.fetchImages(searchQuery)

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `When fetchImages is called and local data is stale Then it fetches from remote and updates DB`() {
        runBlocking {
            val staleTimestamp = currentTimeInSeconds - FETCH_INTERVAL_IN_SECONDS - 100L
            val expectedResult = listOf(sampleImageDetailInDomain)

            given(localImageDataSource.fetchImagesWithTimestamp(searchQuery))
                .willReturn(listOf(sampleImageDetailInDomain) to staleTimestamp)

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

            assertEquals(expectedResult, actualResult)
            verify(localImageDataSource).removeImagesByQuery(searchQuery)
        }
    }

    @Test
    fun `When fetchImages is called and no local data is found Then it fetches from remote`() {
        runBlocking {
            val expectedResult = listOf(sampleImageDetailInDomain)

            given(localImageDataSource.fetchImagesWithTimestamp(searchQuery))
                .willReturn(emptyList<ImageDomainModel>() to 0L)

            given(remoteImageDataSource.fetchImages(searchQuery))
                .willReturn(expectedResult)

            val actualResult = repository.fetchImages(searchQuery)

            assertEquals(expectedResult, actualResult)
        }
    }
}
