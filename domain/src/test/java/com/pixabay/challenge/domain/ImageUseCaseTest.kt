package com.pixabay.challenge.domain

import android.content.res.Resources
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import com.pixabay.challenge.domain.model.ImageDomainModel
import com.pixabay.challenge.domain.repository.ImageRepository
import com.pixabay.challenge.domain.usecase.ImagesUseCase
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FetchImagesUseCaseTest {
    private val searchQuery = "yellow"

    private val image1 = ImageDomainModel(
        id = 1L,
        pageURL = "https://www.example.com",
        type = "photo",
        tags = "nature, outdoors",
        previewURL = "https://www.example.com/preview.jpg",
        webFormatURL = "https://www.example.com/web.jpg",
        largeImageURL = "https://www.example.com/large.jpg",
        downloads = 500L,
        likes = 200L,
        comments = 50L,
        userId = 5678L,
        user = "John Doe",
        userImageURL = "https://www.example.com/user.jpg"
    )

    private val image2 = ImageDomainModel(
        id = 2L,
        pageURL = "https://www.example.com",
        type = "photo",
        tags = "nature, outdoors",
        previewURL = "https://www.example.com/preview.jpg",
        webFormatURL = "https://www.example.com/web.jpg",
        largeImageURL = "https://www.example.com/large.jpg",
        downloads = 500L,
        likes = 200L,
        comments = 50L,
        userId = 5678L,
        user = "John Doe",
        userImageURL = "https://www.example.com/user.jpg"
    )

    @Mock
    lateinit var imagesRepository: ImageRepository

    @Mock
    lateinit var resources: Resources

    private lateinit var fetchImagesUseCase: ImagesUseCase

    @Before
    fun setup() {
        fetchImagesUseCase = ImagesUseCase(imagesRepository)
    }

    @Test
    fun `fetchImages_WithValidSearchQuery_ReturnsListOfImages`() = runBlocking {
        // Given
        val expectedImages = listOf(image1, image2)
        given(imagesRepository.fetchImages(searchQuery)).willReturn(expectedImages)

        var resultImages: List<ImageDomainModel>? = null
        fetchImagesUseCase(searchQuery).collect {
            resultImages = it.data
        }

        // Then
        Assert.assertEquals(expectedImages, resultImages)
    }

    @Test
    fun `fetchImages_WithInvalidResponse_ThrowsHttpException`() = runBlocking {
        // Given
        val message = "An unexpected error occurred"
        val response = Response.error<String>(
            404,
            message.toResponseBody("text/plain".toMediaTypeOrNull())
        )

        whenever(imagesRepository.fetchImages(searchQuery)).thenThrow(HttpException(response))
        val expectedResult = "HTTP 404 " + response.message()

        // When
        var actualMessage: String? = null
        fetchImagesUseCase(searchQuery).collect {
            actualMessage = it.message
        }

        // Then
        Assert.assertEquals(expectedResult, actualMessage)
    }
}