package payback.pixabay.challenge.navigation

sealed class AppScreens(val route: String) {
    companion object {
        const val IMAGE_ID = "image_id"
    }

    object ImageListScreen : AppScreens("image_list")
    object ImageDetailScreen : AppScreens("image_detail")
}
