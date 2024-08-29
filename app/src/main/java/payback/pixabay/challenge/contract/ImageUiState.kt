package payback.pixabay.challenge.presentation.contract

import payback.pixabay.challenge.ui.model.ImageUiModel

data class ImageUiState(
    val isDataLoading: Boolean = false,
    val images: List<ImageUiModel> = emptyList(),
    val errorMessage: String? = null,
    val showDialog: Boolean = false,
    val imageModel: ImageUiModel?=null,
)