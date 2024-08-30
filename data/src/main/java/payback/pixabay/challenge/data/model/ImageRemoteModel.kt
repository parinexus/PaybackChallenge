package payback.pixabay.challenge.data.model

import com.google.gson.annotations.SerializedName

data class ImageRemoteModel(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("pageURL")
    val pageURL: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("tags")
    val tags: String?,
    @SerializedName("previewURL")
    val previewURL: String?,
    @SerializedName("webFormatURL")
    val webFormatURL: String?,
    @SerializedName("largeImageURL")
    val largeImageURL: String?,
    @SerializedName("downloads")
    val downloads: Long?,
    @SerializedName("likes")
    val likes: Long?,
    @SerializedName("comments")
    val comments: Long?,
    @SerializedName("user_id")
    val userId: Long?,
    @SerializedName("user")
    val user: String?,
    @SerializedName("userImageURL")
    val userImageURL: String?
)
