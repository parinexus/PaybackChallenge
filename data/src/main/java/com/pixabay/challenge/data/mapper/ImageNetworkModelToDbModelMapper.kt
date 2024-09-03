package com.pixabay.challenge.data.mapper

import com.pixabay.challenge.data.datastore.local.ImageLocalModel
import com.pixabay.challenge.data.model.ImageRemoteModel
import com.pixabay.challenge.common.orZero

class ImageNetworkModelToDbModelMapper :
    ApiToDbMapper<MapperInput, ImageLocalModel>() {
    override fun map(input: MapperInput): ImageLocalModel {
        val imageDetail = input.apiImageDetails
        return ImageLocalModel(
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
    val apiImageDetails: ImageRemoteModel,
    val searchQuery: String,
    val createdAt: Long
)
