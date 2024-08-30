package payback.pixabay.challenge.data.mapper

import payback.pixabay.challenge.data.datastore.local.ImageLocalModel
import payback.pixabay.challenge.domain.model.ImageDomainModel

class ImageDbModelToDomainModelMapper :
    DbToDomainMapper<ImageLocalModel, ImageDomainModel>() {

    public override fun map(input: ImageLocalModel) = ImageDomainModel(
        id = input.id,
        pageURL = input.pageURL,
        type = input.type,
        tags = input.tags,
        previewURL = input.previewURL,
        webFormatURL = input.webFormatURL,
        largeImageURL = input.largeImageURL,
        downloads = input.downloads,
        likes = input.likes,
        comments = input.comments,
        userId = input.userId,
        user = input.user,
        userImageURL = input.userImageURL
    )
}
