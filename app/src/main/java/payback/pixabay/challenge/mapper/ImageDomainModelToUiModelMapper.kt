package payback.pixabay.challenge.mapper

import payback.pixabay.challenge.common.format
import payback.pixabay.challenge.common.orZero
import payback.pixabay.challenge.domain.model.ImageDomainModel
import payback.pixabay.challenge.ui.model.ImageUiModel
import javax.inject.Inject

class ImageDomainModelToUiModelMapper @Inject constructor() :
    DomainToUiMapper<ImageDomainModel, ImageUiModel>() {
    public override fun map(input: ImageDomainModel) = ImageUiModel(
        id = input.id.orZero(),
        pageURL = input.pageURL,
        type = input.type,
        tags = input.tags.splitTags(),
        previewURL = input.previewURL,
        webFormatURL = input.webFormatURL,
        largeImageURL = input.largeImageURL,
        downloads = input.downloads.format(),
        likes = input.likes.format(),
        comments = input.comments.format(),
        userId = input.userId.orZero(),
        user = input.user,
        userImageURL = input.userImageURL
    )
}

private fun String.splitTags(): List<String> {
    return if (isEmpty()) emptyList()
    else if (contains(",")) {
        split(",")
    } else listOf(this)
}