package payback.pixabay.challenge.presentation.contract

import payback.pixabay.challenge.ui.model.ImageUiModel

data class ImageUiEvent(
    val getImages: (String) -> (Unit),
    val retry: () -> (Unit),
    val showDialog: (Boolean, ImageUiModel?) -> (Unit)
)