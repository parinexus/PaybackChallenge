package payback.pixabay.challenge.data.mapper

import payback.pixabay.challenge.data.model.ImageDetailRemoteModel
import payback.pixabay.challenge.domain.model.ImageDomainModel
import payback.pixabay.challenge.common.orZero

class ImageNetworkModelToDomainModelMapper :
    NetworkToDomainMapper<ImageDetailRemoteModel, ImageDomainModel>() {
    override fun map(input: ImageDetailRemoteModel) = ImageDomainModel(
        id = input.id.orZero(),
        pageURL = input.pageURL.orEmpty(),
        type = input.type.orEmpty(),
        tags = input.tags.orEmpty(),
        previewURL = input.previewURL.orEmpty(),
        webFormatURL = input.webFormatURL.orEmpty(),
        largeImageURL = input.largeImageURL.orEmpty(),
        downloads = input.downloads.orZero(),
        likes = input.likes.orZero(),
        comments = input.comments.orZero(),
        userId = input.userId.orZero(),
        user = input.user.orEmpty(),
        userImageURL = input.userImageURL.orEmpty()
    )
}
