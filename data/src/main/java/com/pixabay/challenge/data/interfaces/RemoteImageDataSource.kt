package com.pixabay.challenge.data.interfaces

import com.pixabay.challenge.common.ResultModel
import com.pixabay.challenge.data.datastore.local.ImageLocalModel
import com.pixabay.challenge.data.model.ImageRemoteModel
import com.pixabay.challenge.domain.model.ImageDomainModel
import kotlinx.coroutines.flow.Flow

interface RemoteImageDataSource {
    fun fetchImages(query: String): Flow<ResultModel<List<ImageLocalModel>>>
}