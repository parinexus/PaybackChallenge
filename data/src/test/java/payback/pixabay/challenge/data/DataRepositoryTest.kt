package payback.pixabay.challenge.data

import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import payback.pixabay.challenge.data.datastore.local.ImageDataModel
import payback.pixabay.challenge.data.datastore.local.ImageRepositoryDao
import payback.pixabay.challenge.data.datastore.remote.ImageApiService
import payback.pixabay.challenge.data.mapper.ImageDbModelToDomainModelMapper
import payback.pixabay.challenge.data.mapper.ImageNetworkModelToDbModelMapper
import payback.pixabay.challenge.data.mapper.ImageNetworkModelToDomainModelMapper
import payback.pixabay.challenge.data.mapper.MapperInput
import payback.pixabay.challenge.data.model.ImageDetailRemoteModel
import payback.pixabay.challenge.data.model.ImagesResponse
import payback.pixabay.challenge.data.repository.ImagesDataRepository
import payback.pixabay.challenge.domain.model.ImageDomainModel

@RunWith(MockitoJUnitRunner::class)
class ImagesDataRepositoryTest {

    private val searchQuery = "blue"
    private val currentTimeInSeconds = System.currentTimeMillis() / 1000L

    private val sampleImageDetailFromApi = ImageDetailRemoteModel(
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

    private val sampleImageDetailFromDb = ImageDataModel(
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
    lateinit var apiService: ImageApiService

    @Mock
    lateinit var repositoryDao: ImageRepositoryDao

    @Mock
    lateinit var networkToDomainMapper: ImageNetworkModelToDomainModelMapper

    @Mock
    lateinit var networkToDbMapper: ImageNetworkModelToDbModelMapper

    @Mock
    lateinit var dbToDomainMapper: ImageDbModelToDomainModelMapper

    private lateinit var repository: ImagesDataRepository

    @Before
    fun setup() {
        repository = ImagesDataRepository(
            imageApiService = apiService,
            imageRepositoryDao = repositoryDao,
            imageNetworkModelToDomainModelMapper = networkToDomainMapper,
            imageNetworkModelToDbModelMapper = networkToDbMapper,
            imageDbModelToDomainModelMapper = dbToDomainMapper
        )
    }

    @Test
    fun `When fetchImages is called Then it returns list of ImageDataModelInDomain from local DB`() {
        runBlocking {
            val expectedResult = listOf(sampleImageDetailInDomain)

            given(repositoryDao.fetchImagesByQuery(searchQuery))
                .willReturn(listOf(sampleImageDetailFromDb))

            given(dbToDomainMapper.toDomain(sampleImageDetailFromDb))
                .willReturn(sampleImageDetailInDomain)

            val actualResult = repository.fetchImages(searchQuery)

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `When fetchImages is called Then it returns list of ImageDataModelInDomain from remote source`() {
        runBlocking {
            val expectedResult = listOf(sampleImageDetailInDomain)

            given(repositoryDao.fetchImagesByQuery(searchQuery))
                .willReturn(emptyList())

            given(apiService.fetchImages(query = searchQuery))
                .willReturn(ImagesResponse(images = listOf(sampleImageDetailFromApi)))

            given(networkToDomainMapper.toDomain(sampleImageDetailFromApi))
                .willReturn(sampleImageDetailInDomain)

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
        }
    }
}
