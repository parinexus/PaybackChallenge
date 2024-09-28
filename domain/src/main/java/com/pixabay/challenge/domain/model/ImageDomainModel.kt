package com.pixabay.challenge.domain.model

data class ImageDomainModel(
    val id: Int,
    val pageURL: String,
    val type: String,
    val tags: String,
    val previewURL: String,
    val webFormatURL: String,
    val largeImageURL: String,
    val downloads: Long,
    val likes: Long,
    val comments: Long,
    val userId: Long,
    val user: String,
    val userImageURL: String
)
