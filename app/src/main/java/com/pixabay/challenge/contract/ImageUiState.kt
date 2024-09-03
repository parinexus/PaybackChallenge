package com.pixabay.challenge.presentation.contract

import com.pixabay.challenge.ui.model.ImageUiModel

data class ImageUiState(
    val isDataLoading: Boolean = false,
    val images: List<ImageUiModel> = emptyList(),
    val errorMessage: String? = null,
    val showDialog: Boolean = false,
    val imageModel: ImageUiModel?=null,
)