package payback.pixabay.challenge.ui.mapper

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import payback.pixabay.challenge.domain.model.ImageDomainModel
import payback.pixabay.challenge.mapper.ImageDomainModelToUiModelMapper
import payback.pixabay.challenge.ui.model.ImageUiModel

@RunWith(Parameterized::class)
class ImageDomainModelToUiModelMapperTest(
    private val input: ImageDomainModel,
    private val expectedResult: ImageUiModel
) {

    companion object {
        private val input1 = ImageDomainModel(
            id = 1,
            pageURL = "https://example.com",
            type = "photo",
            tags = "nature,landscape",
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
        private val expectedResult1 = ImageUiModel(
            id = 1,
            pageURL = "https://example.com",
            type = "photo",
            tags = listOf("nature", "landscape"),
            previewURL = "https://example.com/preview",
            webFormatURL = "https://example.com/webformat",
            largeImageURL = "https://example.com/large",
            downloads = "50",
            likes = "10",
            comments = "5",
            userId = 123,
            user = "johndoe",
            userImageURL = "https://example.com/user"
        )

        private val input2 = ImageDomainModel(
            id = 2,
            pageURL = "https://example.com",
            type = "Scenery",
            tags = "nature,landscape",
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
        private val expectedResult2 = ImageUiModel(
            id = 2,
            pageURL = "https://example.com",
            type = "Scenery",
            tags = listOf("nature", "landscape"),
            previewURL = "https://example.com/preview",
            webFormatURL = "https://example.com/webformat",
            largeImageURL = "https://example.com/large",
            downloads = "50",
            likes = "10",
            comments = "5",
            userId = 123888,
            user = "johndoe",
            userImageURL = "https://example.com/user"
        )

        @JvmStatic
        @Parameterized.Parameters(name = "Given {0} When toUi Then returns {1}")
        fun params() = listOf(
            arrayOf(input1, expectedResult1),
            arrayOf(input2, expectedResult2)
        )
    }

    private lateinit var classUnderTest: ImageDomainModelToUiModelMapper

    @Before
    fun setup() {
        classUnderTest = ImageDomainModelToUiModelMapper()
    }

    @Test
    fun `Given domain model When mapped to UI model Then returns expected UI model`() {
        // When
        val actualResult = classUnderTest.toUi(input)

        // Then
        assertEquals(expectedResult, actualResult)
    }
}
