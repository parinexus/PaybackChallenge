package com.pixabay.challenge.data.model

import com.google.gson.annotations.SerializedName

data class ImagesResponse(
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalHits: Int,
    @SerializedName("hits")
    val images: List<ImageRemoteModel>,
)
