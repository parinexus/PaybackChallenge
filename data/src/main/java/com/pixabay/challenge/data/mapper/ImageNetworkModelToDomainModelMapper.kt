package com.pixabay.challenge.data.mapper

import com.pixabay.challenge.data.model.ImageRemoteModel
import com.pixabay.challenge.domain.model.ImageDomainModel
import com.pixabay.challenge.common.orZero

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
