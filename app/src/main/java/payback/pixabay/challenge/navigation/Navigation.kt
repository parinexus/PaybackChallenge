package payback.pixabay.challenge.navigation

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import payback.pixabay.challenge.navigation.AppScreens.Companion.IMAGE_ID
import payback.pixabay.challenge.ui.imagedetail.ImageInfoScreen
import payback.pixabay.challenge.ui.imagelist.ImageListScreen
import payback.pixabay.challenge.ui.model.ImageUiModel

@ExperimentalComposeUiApi
@ExperimentalLayoutApi
@ExperimentalMaterialApi
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppScreens.ImageListScreen.route) {
        composable(AppScreens.ImageListScreen.route) {
            ImageListScreen(navController)
        }
        composable(
            AppScreens.ImageDetailScreen.route
        ) {
            val imageDetail =
                navController.previousBackStackEntry?.savedStateHandle?.get<ImageUiModel>(
                    IMAGE_ID
                )
            imageDetail?.let {
                ImageInfoScreen(it, navController)
            }
        }
    }
}

