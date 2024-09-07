package com.pixabay.challenge.ui.imagelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pixabay.challenge.R
import com.pixabay.challenge.navigation.AppScreens
import com.pixabay.challenge.navigation.AppScreens.Companion.IMAGE_ID
import com.pixabay.challenge.viewmodel.ImagesViewModel
import com.pixabay.challenge.contract.ImageUiEvent
import com.pixabay.challenge.ui.components.EmptyStateView
import com.pixabay.challenge.ui.components.QueryInputField
import com.pixabay.challenge.ui.components.RetryAbleErrorView

@ExperimentalComposeUiApi
@ExperimentalLayoutApi
@ExperimentalMaterialApi
@Composable
fun ImageListScreen(
    navController: NavController,
    viewModel: ImagesViewModel = hiltViewModel()
) {
    val imageEvents = ImageUiEvent(
        getImages = { viewModel.fetchImages(it) },
        retry = {
            viewModel.retry()
        },
        showDialog = { isShowAble, imageUiModel -> viewModel.showDialog(isShowAble, imageUiModel) },
    )
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            val softKeyboardController = LocalSoftwareKeyboardController.current

            QueryInputField(
                onSearch = { searchQuery ->
                    imageEvents.getImages(searchQuery)
                    softKeyboardController?.hide()
                }
            )

            when {
                state.isDataLoading -> {
                    Box(modifier = Modifier.size(70.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = MaterialTheme.colors.onBackground)
                    }
                }

                state.errorMessage?.isNotBlank() == true -> {
                    RetryAbleErrorView(state.errorMessage) {
                        imageEvents.retry()
                    }
                }

                state.images.isEmpty() -> {
                    EmptyStateView()
                }

                else -> {
                    ImageCollectionView(
                        modifier = Modifier,
                        list = state.images
                    ) { imageData ->
                        imageEvents.showDialog(true, imageData)
                    }
                }
            }


            state.imageModel?.let {
                if (state.showDialog) {
                    ImageDetailWarningDialog(
                        imageDetail = it,
                        onPositive = {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                IMAGE_ID,
                                it
                            )
                            navController.navigate(AppScreens.ImageDetailScreen.route)
                            imageEvents.showDialog(false, null)
                        },
                        dismissDialog = {
                            imageEvents.showDialog(false, null)
                        }
                    )
                }
            }
        }
    }
}
