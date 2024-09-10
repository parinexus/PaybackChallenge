package com.pixabay.challenge.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageUiModel(
    val tags: List<String>,
    val previewURL: String,
    val largeImageURL: String,
    val downloads: String,
    val likes: String,
    val comments: String,
    val user: String,
): Parcelable