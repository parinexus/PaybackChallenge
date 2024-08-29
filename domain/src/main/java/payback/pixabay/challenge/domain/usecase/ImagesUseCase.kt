package payback.pixabay.challenge.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import payback.pixabay.challenge.common.ResultModel
import payback.pixabay.challenge.domain.model.ImageDomainModel
import payback.pixabay.challenge.domain.repository.ImageRepository
import javax.inject.Inject

class ImagesUseCase @Inject constructor(
    private val imagesRepository: ImageRepository,
) {
    operator fun invoke(query: String): Flow<ResultModel<List<ImageDomainModel>>> =
        flow {
            runCatching { imagesRepository.fetchImages(query) }
                .onSuccess { images ->
                    emit(ResultModel.Success(images))
                }.onFailure { e ->
                    emit(ResultModel.Error(e.localizedMessage))
                }
        }
}
