package com.pixabay.challenge.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import com.pixabay.challenge.data.datastore.local.ImageLocalModel
import com.pixabay.challenge.data.model.ImageRemoteModel

@RunWith(Parameterized::class)
class ImageNetworkModelToDbModelMapperTest(
    private val input: MapperInput,
    private val expectedResult: ImageLocalModel
) {

    companion object {
        private const val searchQuery1 = "flower"
        private const val searchQuery2 = "wall"
        private val systemCurrentTimeInSeconds = System.currentTimeMillis() / 1000L

        private val input1 = ImageRemoteModel(
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
        private val expectedResult1 = ImageLocalModel(
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
            searchQuery = searchQuery1,
            createdAt = systemCurrentTimeInSeconds
        )

        private val input2 = ImageRemoteModel(
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
        private val expectedResult2 = ImageLocalModel(
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
            searchQuery = searchQuery2,
            createdAt = systemCurrentTimeInSeconds
        )

        @JvmStatic
        @Parameterized.Parameters(name = "Given {0} When toDatabase Then returns {1}")
        fun params() = listOf(
            arrayOf(MapperInput(input1, searchQuery1, systemCurrentTimeInSeconds), expectedResult1),
            arrayOf(MapperInput(input2, searchQuery2, systemCurrentTimeInSeconds), expectedResult2)
        )
    }

    private lateinit var imageNetworkModelToDbModelMapper: ImageNetworkModelToDbModelMapper

    @Before
    fun setup() {
        imageNetworkModelToDbModelMapper = ImageNetworkModelToDbModelMapper()
    }

    @Test
    fun given_networkModel_then_returns_expectedDatabaseModel() {
        val actualResult = imageNetworkModelToDbModelMapper.toDatabase(input)

        assertEquals(expectedResult, actualResult)
    }
}
