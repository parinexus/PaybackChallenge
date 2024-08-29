package payback.pixabay.challenge.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import payback.pixabay.challenge.data.datastore.local.ImageDataModel
import payback.pixabay.challenge.domain.model.ImageDataModelInDomain

@RunWith(Parameterized::class)
class ImageDbModelToDomainMapper(
    private val input: ImageDataModel,
    private val expectedResult: ImageDataModelInDomain
) {

    companion object {
        private val systemCurrentTimeInSeconds = System.currentTimeMillis() / 1000L

        private val input1 = ImageDataModel(
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
            searchQuery = "balloon",
            createdAt = systemCurrentTimeInSeconds
        )
        private val expectedResult1 = ImageDataModelInDomain(
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

        private val input2 = ImageDataModel(
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
            user = "johndoe",
            userImageURL = "https://example.com/user",
            searchQuery = "Scenery",
            createdAt = systemCurrentTimeInSeconds
        )
        private val expectedResult2 = ImageDataModelInDomain(
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
            user = "johndoe",
            userImageURL = "https://example.com/user"
        )

        @JvmStatic
        @Parameterized.Parameters(name = "Given {0} When toDomain Then returns {1}")
        fun params() = listOf(
            arrayOf(input1, expectedResult1),
            arrayOf(input2, expectedResult2)
        )
    }

    private lateinit var classUnderTest: ImageDbModelToDomainModelMapper

    @Before
    fun setup() {
        classUnderTest = ImageDbModelToDomainModelMapper()
    }

    @Test
    fun `Given database model When toDomain then returns expected domain model`() {
        // When
        val actualResult = classUnderTest.toDomain(input)

        // Then
        assertEquals(expectedResult, actualResult)
    }
}
