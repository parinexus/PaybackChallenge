package com.pixabay.challenge.data

import com.nhaarman.mockitokotlin2.whenever
import com.pixabay.challenge.data.datastore.local.ImageLocalModel
import com.pixabay.challenge.data.datastore.local.ImageRepositoryDao
import com.pixabay.challenge.data.interfaces.LocalImageDataSourceImpl
import com.pixabay.challenge.data.mapper.ImageDbModelToDomainModelMapper
import com.pixabay.challenge.domain.model.ImageDomainModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class LocalImageDataSourceImplTest {

    @Mock
    lateinit var imageRepositoryDao: ImageRepositoryDao

    @Mock
    lateinit var imageDbModelToDomainModelMapper: ImageDbModelToDomainModelMapper

    private lateinit var localImageDataSourceImpl: LocalImageDataSourceImpl

    private val searchQuery = "fruits"
    private val sampleImageDbModel = ImageLocalModel(
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
        userImageURL = "https://example.com/user",
        searchQuery = searchQuery,
        createdAt = 100L
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
        localImageDataSourceImpl = LocalImageDataSourceImpl(
            imageRepositoryDao = imageRepositoryDao,
            imageDbModelToDomainModelMapper = imageDbModelToDomainModelMapper
        )
    }

    @Test
    fun fetchImagesWithTimestamp_returnsDomainModelsAndTimestamp_whenImagesArePresent() = runBlocking {
        val imagesWithTimestamp = listOf(sampleImageDbModel)
        whenever(imageRepositoryDao.fetchImagesByQueryWithTimestamps(searchQuery))
            .thenReturn(imagesWithTimestamp)
        whenever(imageDbModelToDomainModelMapper.toDomain(sampleImageDbModel))
            .thenReturn(sampleImageDomainModel)

        val (domainModels, timestamp) = localImageDataSourceImpl.fetchImagesWithTimestamp(searchQuery)

        assertEquals(listOf(sampleImageDomainModel), domainModels)
        assertEquals(100L, timestamp)

        verify(imageRepositoryDao).fetchImagesByQueryWithTimestamps(searchQuery)
        verify(imageDbModelToDomainModelMapper).toDomain(sampleImageDbModel)
        verifyNoMoreInteractions(imageRepositoryDao, imageDbModelToDomainModelMapper)
    }

    @Test
    fun fetchImagesWithTimestamp_returnsEmptyListAndZeroTimestamp_whenNoImagesAreFound() = runBlocking {
        whenever(imageRepositoryDao.fetchImagesByQueryWithTimestamps(searchQuery))
            .thenReturn(emptyList())

        val (domainModels, timestamp) = localImageDataSourceImpl.fetchImagesWithTimestamp(searchQuery)

        assertEquals(emptyList<ImageDomainModel>(), domainModels)
        assertEquals(0L, timestamp)

        verify(imageRepositoryDao).fetchImagesByQueryWithTimestamps(searchQuery)
        verifyNoMoreInteractions(imageDbModelToDomainModelMapper)
    }

    @Test
    fun fetchImages_returnsMappedDomainModels_whenImagesArePresent() = runBlocking {
        whenever(imageRepositoryDao.fetchImagesByQuery(searchQuery))
            .thenReturn(listOf(sampleImageDbModel))
        whenever(imageDbModelToDomainModelMapper.toDomain(sampleImageDbModel))
            .thenReturn(sampleImageDomainModel)

        val actualResult = localImageDataSourceImpl.fetchImages(searchQuery)

        assertEquals(listOf(sampleImageDomainModel), actualResult)

        verify(imageRepositoryDao).fetchImagesByQuery(searchQuery)
        verify(imageDbModelToDomainModelMapper).toDomain(sampleImageDbModel)
        verifyNoMoreInteractions(imageRepositoryDao, imageDbModelToDomainModelMapper)
    }

    @Test
    fun fetchImages_returnsEmptyList_whenNoImagesAreFound() = runBlocking {
        whenever(imageRepositoryDao.fetchImagesByQuery(searchQuery)).thenReturn(emptyList())

        val actualResult = localImageDataSourceImpl.fetchImages(searchQuery)

        assertEquals(emptyList<ImageDomainModel>(), actualResult)

        verify(imageRepositoryDao).fetchImagesByQuery(searchQuery)
        verifyNoMoreInteractions(imageDbModelToDomainModelMapper)
    }

    @Test
    fun removeImagesByQuery_callsRepository_toRemoveImages() = runBlocking {
        localImageDataSourceImpl.removeImagesByQuery(searchQuery)

        verify(imageRepositoryDao).removeImagesByQuery(searchQuery)
        verifyNoMoreInteractions(imageRepositoryDao)
    }

    @Test
    fun saveImages_savesImages_toTheDatabase() = runBlocking {
        val images = listOf(sampleImageDbModel)

        localImageDataSourceImpl.saveImages(images)

        verify(imageRepositoryDao).saveImages(images)
        verifyNoMoreInteractions(imageRepositoryDao)
    }

    @Test
    fun saveImages_doesNothing_ifImagesListIsNull() = runBlocking {
        localImageDataSourceImpl.saveImages(null)

        verifyNoInteractions(imageRepositoryDao)
    }
}
