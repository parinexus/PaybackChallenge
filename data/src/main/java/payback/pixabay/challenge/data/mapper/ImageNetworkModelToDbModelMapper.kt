package payback.pixabay.challenge.data.mapper

import payback.pixabay.challenge.data.datastore.local.ImageDataModel
import payback.pixabay.challenge.data.model.ImageDetailRemoteModel
import payback.pixabay.challenge.common.orZero

class ImageNetworkModelToDbModelMapper :
    ApiToDbMapper<MapperInput, ImageDataModel>() {
    override fun map(input: MapperInput): ImageDataModel {
        val imageDetail = input.apiImageDetails
        return ImageDataModel(
            id = imageDetail.id.orZero(),
            pageURL = imageDetail.pageURL.orEmpty(),
            type = imageDetail.type.orEmpty(),
            tags = imageDetail.tags.orEmpty(),
            previewURL = imageDetail.previewURL.orEmpty(),
            webFormatURL = imageDetail.webFormatURL.orEmpty(),
            largeImageURL = imageDetail.largeImageURL.orEmpty(),
            downloads = imageDetail.downloads.orZero(),
            likes = imageDetail.likes.orZero(),
            comments = imageDetail.comments.orZero(),
            userId = imageDetail.userId.orZero(),
            user = imageDetail.user.orEmpty(),
            userImageURL = imageDetail.userImageURL.orEmpty(),
            searchQuery = input.searchQuery,
            createdAt = input.createdAt
        )
    }
}

data class MapperInput(
    val apiImageDetails: ImageDetailRemoteModel,
    val searchQuery: String,
    val createdAt: Long
)
