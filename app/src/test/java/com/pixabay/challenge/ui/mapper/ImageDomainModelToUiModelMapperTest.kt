package com.pixabay.challenge.ui.mapper

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import com.pixabay.challenge.domain.model.ImageDomainModel
import com.pixabay.challenge.mapper.ImageDomainModelToUiModelMapper
import com.pixabay.challenge.ui.model.ImageUiModel

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
            user = "test1",
            userImageURL = "https://example.com/user"
        )
        private val expectedResult1 = ImageUiModel(
            tags = listOf("nature", "landscape"),
            previewURL = "https://example.com/preview",
            largeImageURL = "https://example.com/large",
            downloads = "50",
            likes = "10",
            comments = "5",
            user = "test1",
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
            user = "test3",
            userImageURL = "https://example.com/user"
        )
        private val expectedResult2 = ImageUiModel(
            tags = listOf("nature", "landscape"),
            previewURL = "https://example.com/preview",
            largeImageURL = "https://example.com/large",
            downloads = "50",
            likes = "10",
            comments = "5",
            user = "test3",
        )

        @JvmStatic
        @Parameterized.Parameters(name = "Given {0} When toUi Then returns {1}")
        fun params() = listOf(
            arrayOf(input1, expectedResult1),
            arrayOf(input2, expectedResult2)
        )
    }

    private lateinit var imageDomainModelToUiModelMapper: ImageDomainModelToUiModelMapper

    @Before
    fun setup() {
        imageDomainModelToUiModelMapper = ImageDomainModelToUiModelMapper()
    }

    @Test
    fun given_domainModel_then_returns_expectedUiModel() {
        val actualResult = imageDomainModelToUiModelMapper.toUi(input)

        assertEquals(expectedResult, actualResult)
    }
}
