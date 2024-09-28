package com.pixabay.challenge.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.pixabay.challenge.common.ResultModel
import com.pixabay.challenge.domain.model.ImageDomainModel
import com.pixabay.challenge.domain.repository.ImageRepository

class ImagesUseCase(
    private val imagesRepository: ImageRepository,
) {
    operator fun invoke(query: String): Flow<ResultModel<List<ImageDomainModel>>> = flow {
        runCatching { imagesRepository.fetchImages(query) }
            .onSuccess { result ->
                if (result.data.isNullOrEmpty()) {
                    emit(ResultModel.Error("No images found for the query: $query"))
                } else {
                    emit(result)
                }
                emit(result)
            }
            .onFailure { e ->
                emit(ResultModel.Error(e.localizedMessage))
            }
    }
}