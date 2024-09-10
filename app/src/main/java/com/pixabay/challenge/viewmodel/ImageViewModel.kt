package com.pixabay.challenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.pixabay.challenge.common.ResultModel
import com.pixabay.challenge.domain.model.ImageDomainModel
import com.pixabay.challenge.domain.usecase.ImagesUseCase
import com.pixabay.challenge.mapper.ImageDomainModelToUiModelMapper
import com.pixabay.challenge.contract.ImageUiState
import com.pixabay.challenge.ui.model.ImageUiModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val INITIAL_SEARCH_QUERY = "fruits"

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val imagesUseCase: ImagesUseCase,
    private val imageMapper: ImageDomainModelToUiModelMapper,
) : ViewModel() {

    private var lastQuery: String = INITIAL_SEARCH_QUERY
    private val _uiState = MutableStateFlow(ImageUiState())
    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        ImageUiState()
    )

    init {
        fetchImages(lastQuery)
    }

    fun fetchImages(query: String) {
        updateLoadingState(isLoading = true)

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                imagesUseCase(query).first()
            }

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
        fetchImages(lastQuery)
    }

    fun showDialog(isVisible: Boolean, image: ImageUiModel?) {
        _uiState.update { it.copy(showDialog = isVisible, imageModel = image) }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        _uiState.update { it.copy(isDataLoading = isLoading) }
    }
}