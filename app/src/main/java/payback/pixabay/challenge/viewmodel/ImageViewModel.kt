package payback.pixabay.challenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import payback.pixabay.challenge.common.ResultModel
import payback.pixabay.challenge.domain.model.ImageDomainModel
import payback.pixabay.challenge.domain.usecase.ImagesUseCase
import payback.pixabay.challenge.mapper.ImageDomainModelToUiModelMapper
import payback.pixabay.challenge.presentation.contract.ImageUiState
import payback.pixabay.challenge.ui.model.ImageUiModel
import javax.inject.Inject

private const val INITIAL_SEARCH_QUERY = "fruits"

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val imagesUseCase: ImagesUseCase,
    private val imageMapper: ImageDomainModelToUiModelMapper,
) : ViewModel() {

    private var lastQuery: String? = null
    private val _uiState = MutableStateFlow(ImageUiState())
    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        ImageUiState()
    )

    init {
        fetchImages(INITIAL_SEARCH_QUERY)
    }

    fun fetchImages(query: String) {
        lastQuery = query
        updateLoadingState(true)
        viewModelScope.launch(Dispatchers.IO) {
            imagesUseCase(query).collect { result ->
                when (result) {
                    is ResultModel.Success -> handleSuccess(result)
                    is ResultModel.Error -> {
                        _uiState.update {
                            it.copy(
                                isDataLoading = false,
                                errorMessage = result.message.orEmpty()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handleSuccess(result: ResultModel.Success<List<ImageDomainModel>>) {
        val images = mapImages(result.data)
        updateUiState(images = images)
    }

    private fun mapImages(data: List<ImageDomainModel>?): List<ImageUiModel> {
        return data?.map { image ->
            imageMapper.toUi(image)
        } ?: emptyList()
    }

    private fun updateUiState(
        isLoading: Boolean = false,
        images: List<ImageUiModel> = emptyList(),
        errorMessage: String? = null
    ) {
        _uiState.update {
            it.copy(
                isDataLoading = isLoading,
                images = images,
                errorMessage = errorMessage
            )
        }
    }

    fun retry() {
        lastQuery?.let { fetchImages(it) }
    }

    fun showDialog(isVisible: Boolean, image: ImageUiModel?) {
        _uiState.update { it.copy(showDialog = isVisible, imageModel = image) }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        _uiState.update { it.copy(isDataLoading = isLoading) }
    }
}