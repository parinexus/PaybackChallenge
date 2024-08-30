package payback.pixabay.challenge.data.model

import com.google.gson.annotations.SerializedName

data class ImagesResponse(
    @SerializedName("hits")
    var images: List<ImageRemoteModel>?
)
