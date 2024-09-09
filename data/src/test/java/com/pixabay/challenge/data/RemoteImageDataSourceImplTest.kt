package com.pixabay.challenge.data

import com.nhaarman.mockitokotlin2.whenever
import com.pixabay.challenge.data.datastore.local.ImageLocalModel
import com.pixabay.challenge.data.datastore.remote.ImageApiService
import com.pixabay.challenge.data.interfaces.LocalImageDataSource
import com.pixabay.challenge.data.interfaces.RemoteImageDataSourceImpl
import com.pixabay.challenge.data.mapper.ImageNetworkModelToDbModelMapper
import com.pixabay.challenge.data.mapper.ImageNetworkModelToDomainModelMapper
import com.pixabay.challenge.data.mapper.MapperInput
import com.pixabay.challenge.data.model.ImageRemoteModel
import com.pixabay.challenge.data.model.ImagesResponse
import com.pixabay.challenge.domain.model.ImageDomainModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.mockito.Mock
import kotlin.math.abs
import org.junit.Before
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.`when`
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RemoteImageDataSourceImplTest {

    @Mock
    lateinit var imageApiService: ImageApiService

    @Mock
    lateinit var localImageDataSource: LocalImageDataSource

    @Mock
    lateinit var imageNetworkModelToDomainModelMapper: ImageNetworkModelToDomainModelMapper

    @Mock
    lateinit var imageNetworkModelToDbModelMapper: ImageNetworkModelToDbModelMapper

    private lateinit var remoteImageDataSourceImpl: RemoteImageDataSourceImpl

    private val searchQuery = "nature"

    private val sampleApiImageDetail = ImageRemoteModel(
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

    private val sampleImageDomainModel = ImageDomainModel(
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

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        remoteImageDataSourceImpl = RemoteImageDataSourceImpl(
            imageApiService = imageApiService,
            localImageDataSource = localImageDataSource,
            imageNetworkModelToDomainModelMapper = imageNetworkModelToDomainModelMapper,
            imageNetworkModelToDbModelMapper = imageNetworkModelToDbModelMapper
        )
    }

    @Test
    fun `fetchImages returns empty list when API response is null`() = runBlocking {
        `when`(imageApiService.fetchImages(query = searchQuery)).thenReturn(ImagesResponse(images = null))

        val actualResult = remoteImageDataSourceImpl.fetchImages(searchQuery)

        assertEquals(emptyList<ImageDomainModel>(), actualResult)
        verify(imageApiService).fetchImages(query = searchQuery)
        verify(localImageDataSource, Mockito.never()).saveImages(emptyList())

        verifyNoMoreInteractions(imageNetworkModelToDomainModelMapper)
    }

    @Test
    fun `fetchImages maps API response to domain models`() = runBlocking {
        val searchQuery = "test"

        val apiResponse = ImagesResponse(
            images = listOf(sampleApiImageDetail)
        )

        whenever(imageApiService.fetchImages(query = searchQuery)).thenReturn(apiResponse)
        whenever(imageNetworkModelToDomainModelMapper.toDomain(sampleApiImageDetail)).thenReturn(sampleImageDomainModel)

        val actualResult = remoteImageDataSourceImpl.fetchImages(searchQuery)

        assertEquals(listOf(sampleImageDomainModel), actualResult)

        verify(imageApiService).fetchImages(query = searchQuery)
        verify(imageNetworkModelToDomainModelMapper).toDomain(sampleApiImageDetail)
        verifyNoMoreInteractions(imageNetworkModelToDomainModelMapper)
    }
    @Test
    fun `fetchImages maps API response to domain models and saves images to the local database`() = runBlocking {
        val searchQuery = "test"

        val apiResponse = ImagesResponse(
            images = listOf(sampleApiImageDetail)
        )

        val dbModel = ImageLocalModel(
            id = 1,
            searchQuery = searchQuery,
            pageURL = "http://example.com",
            type = "photo",
            tags = "tag1,tag2",
            previewURL = "http://example.com/preview",
            webFormatURL = "http://example.com/web",
            largeImageURL = "http://example.com/large",
            downloads = 100,
            likes = 10,
            comments = 5,
            userId = 1,
            user = "user",
            userImageURL = "http://example.com/userimage",
            createdAt = System.currentTimeMillis() / 1000L
        )

        val mapperInput = MapperInput(sampleApiImageDetail, searchQuery, dbModel.createdAt)

        whenever(imageApiService.fetchImages(query = searchQuery)).thenReturn(apiResponse)
        whenever(imageNetworkModelToDomainModelMapper.toDomain(sampleApiImageDetail)).thenReturn(sampleImageDomainModel)
        whenever(imageNetworkModelToDbModelMapper.toDatabase(mapperInput)).thenReturn(dbModel)

        remoteImageDataSourceImpl.fetchImages(searchQuery)

        verify(imageApiService).fetchImages(query = searchQuery)
        verify(imageNetworkModelToDomainModelMapper).toDomain(sampleApiImageDetail)
        verify(imageNetworkModelToDbModelMapper).toDatabase(mapperInput)
        verify(localImageDataSource).saveImages(listOf(dbModel))
        verifyNoMoreInteractions(imageApiService, imageNetworkModelToDomainModelMapper, imageNetworkModelToDbModelMapper, localImageDataSource)
    }

    @Test
    fun `saveImagesWithinDataBase does not save anything when API response is empty`() = runBlocking {
        val emptyImagesResponse = ImagesResponse(images = emptyList())
        val query = "test"

        remoteImageDataSourceImpl.saveImagesWithinDataBase(emptyImagesResponse, query)

        verify(localImageDataSource, Mockito.never()).saveImages(emptyList())
        verifyNoMoreInteractions(imageNetworkModelToDbModelMapper)
    }

    @Test
    fun `getCurrentTimeInSeconds returns current time in seconds`() {
        val currentTimeInSeconds = remoteImageDataSourceImpl.getCurrentTimeInSeconds()

        val expectedTimeInSeconds = System.currentTimeMillis() / 1000L
        assertTrue(abs(expectedTimeInSeconds - currentTimeInSeconds) <= 1)
    }
}
