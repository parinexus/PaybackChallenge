package payback.pixabay.challenge.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import payback.pixabay.challenge.data.model.ImageDetailRemoteModel
import payback.pixabay.challenge.domain.model.ImageDomainModel

@RunWith(Parameterized::class)
class ImageNetworkToDomainModelMapper(
    private val input: ImageDetailRemoteModel,
    private val expectedResult: ImageDomainModel
) {

    companion object {
        private val input1 = ImageDetailRemoteModel(
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
            user = "johndoe",
            userImageURL = "https://example.com/user"
        )

        private val input2 = ImageDetailRemoteModel(
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

    private lateinit var classUnderTest: ImageNetworkModelToDomainModelMapper

    @Before
    fun setup() {
        classUnderTest = ImageNetworkModelToDomainModelMapper()
    }

    @Test
    fun `Given database model When toDomain then returns expected domain model`() {
        // When
        val actualResult = classUnderTest.toDomain(input)

        // Then
        assertEquals(expectedResult, actualResult)
    }
}
