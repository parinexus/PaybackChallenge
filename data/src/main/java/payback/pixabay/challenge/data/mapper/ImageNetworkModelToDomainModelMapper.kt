package payback.pixabay.challenge.data.mapper

import payback.pixabay.challenge.data.model.ImageRemoteModel
import payback.pixabay.challenge.domain.model.ImageDomainModel
import payback.pixabay.challenge.common.orZero

class ImageNetworkModelToDomainModelMapper :
    NetworkToDomainMapper<ImageRemoteModel, ImageDomainModel>() {
    override fun map(input: ImageRemoteModel) = ImageDomainModel(
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
