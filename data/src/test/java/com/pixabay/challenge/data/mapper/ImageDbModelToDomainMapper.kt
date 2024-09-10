package com.pixabay.challenge.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import com.pixabay.challenge.data.datastore.local.ImageLocalModel
import com.pixabay.challenge.domain.model.ImageDomainModel

@RunWith(Parameterized::class)
class ImageDbModelToDomainMapper(
    private val input: ImageLocalModel,
    private val expectedResult: ImageDomainModel
) {

    companion object {
        private val systemCurrentTimeInSeconds = System.currentTimeMillis() / 1000L

        private val input1 = ImageLocalModel(
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
            searchQuery = "balloon",
            createdAt = systemCurrentTimeInSeconds
        )
        private val expectedResult1 = ImageDomainModel(
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

        private val input2 = ImageLocalModel(
            id = 2,
            pageURL = "https://example.com",
            type = "Scenery",
            tags = "nature, landscape",
            previewURL = "https://example.com/preview",
            webFormatURL = "https://example.com/webformat",
            largeImageURL = "https://example.com/large",
            downloads = 50,
            likes = 10,
            comments = 5,
            userId = 123888,
            user = "test2",
            userImageURL = "https://example.com/user",
            searchQuery = "Scenery",
            createdAt = systemCurrentTimeInSeconds
        )
        private val expectedResult2 = ImageDomainModel(
            id = 2,
            pageURL = "https://example.com",
            type = "Scenery",
            tags = "nature, landscape",
            previewURL = "https://example.com/preview",
            webFormatURL = "https://example.com/webformat",
            largeImageURL = "https://example.com/large",
            downloads = 50,
            likes = 10,
            comments = 5,
            userId = 123888,
            user = "test2",
            userImageURL = "https://example.com/user"
        )

        @JvmStatic
        @Parameterized.Parameters(name = "Given {0} When toDomain Then returns {1}")
        fun params() = listOf(
            arrayOf(input1, expectedResult1),
            arrayOf(input2, expectedResult2)
        )
    }

    private lateinit var imageDbModelToDomainModelMapper: ImageDbModelToDomainModelMapper

    @Before
    fun setup() {
        imageDbModelToDomainModelMapper = ImageDbModelToDomainModelMapper()
    }

    @Test
    fun given_databaseModel_then_returns_expectedDomainModel() {
        val actualResult = imageDbModelToDomainModelMapper.toDomain(input)

        assertEquals(expectedResult, actualResult)
    }
}
