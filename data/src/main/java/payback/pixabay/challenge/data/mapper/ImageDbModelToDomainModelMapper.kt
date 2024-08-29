package payback.pixabay.challenge.data.mapper

import payback.pixabay.challenge.data.datastore.local.ImageDataModel
import payback.pixabay.challenge.domain.model.ImageDataModelInDomain

class ImageDbModelToDomainModelMapper :
    DbToDomainMapper<ImageDataModel, ImageDataModelInDomain>() {

    public override fun map(input: ImageDataModel) = ImageDataModelInDomain(
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
