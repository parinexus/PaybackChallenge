package com.pixabay.challenge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pixabay.challenge.navigation.AppScreens.Companion.IMAGE_ID
import com.pixabay.challenge.ui.imagedetail.ImageInfoScreen
import com.pixabay.challenge.ui.imagelist.ImageListScreen
import com.pixabay.challenge.ui.model.ImageUiModel

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppScreens.ImageListScreen.route) {
        composable(AppScreens.ImageListScreen.route) {
            ImageListScreen(
                navigateToImageDetail = { imageUiModel ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        IMAGE_ID,
                        imageUiModel
                    )
                    navController.navigate(AppScreens.ImageDetailScreen.route)
                }
            )
        }

        composable(AppScreens.ImageDetailScreen.route) {
            val imageDetail =
                navController.previousBackStackEntry?.savedStateHandle?.get<ImageUiModel>(IMAGE_ID)
            imageDetail?.let {
                ImageInfoScreen(imageDetail, popBackStack = {
                    navController.popBackStack()
                })
            }
        }
    }
}
