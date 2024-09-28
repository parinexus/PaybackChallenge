package com.pixabay.challenge.ui.imagelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.pixabay.challenge.R
import com.pixabay.challenge.contract.ImageUiEvent
import com.pixabay.challenge.contract.ImageUiState
import com.pixabay.challenge.ui.components.EmptyStateView
import com.pixabay.challenge.viewmodel.ImagesViewModel
import com.pixabay.challenge.ui.components.QueryInputField
import com.pixabay.challenge.ui.components.RetryAbleErrorView
import com.pixabay.challenge.ui.model.ImageUiModel

@Composable
fun ImageListScreen(
    viewModel: ImagesViewModel = hiltViewModel(),
    navigateToImageDetail: (imageUiModel: ImageUiModel) -> Unit
    ) {
    val imageEvents = ImageUiEvent(
        onSearchImages = { viewModel.fetchImages(it) },
        retry = { viewModel.retry() },
        showDialog = { isShowAble, imageUiModel -> viewModel.showDialog(isShowAble, imageUiModel) }
    )

    val state by viewModel.uiState.collectAsState()
    SearchScreenLoader(
        viewState = state,
        actions = imageEvents,
        navigateToImageDetail = navigateToImageDetail
    )
}

@Composable
fun SearchScreenLoader(
    viewState: ImageUiState,
    actions: ImageUiEvent,
    navigateToImageDetail: (imageUiModel: ImageUiModel) -> Unit,
) {
    SearchScreenScaffold(
        viewState = viewState,
        actions = actions,
        navigateToImageDetail = navigateToImageDetail
    )
}

@Composable
private fun SearchScreenScaffold(
    viewState: ImageUiState,
    actions: ImageUiEvent,
    navigateToImageDetail: (imageUiModel: ImageUiModel) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            QueryInputField(
                onSearch = { searchQuery -> actions.onSearchImages(searchQuery) },
            )

            MainContent(
                viewState = viewState,
                actions = actions,
                navigateToImageDetail = navigateToImageDetail
            )
        }
    }
}

@Composable
private fun MainContent(
    viewState: ImageUiState,
    actions: ImageUiEvent,
    navigateToImageDetail: (imageUiModel: ImageUiModel) -> Unit,
) {
    when {
        viewState.isDataLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .size(dimensionResource(id = R.dimen.progress_bar_size)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colors.onBackground)
            }
        }

        viewState.errorMessage?.isNotEmpty() == true -> {
            RetryAbleErrorView(viewState.errorMessage) {
                actions.retry()
            }
        }

        viewState.images.isEmpty() -> {
            EmptyStateView()
        }

        else -> {
            ImageCollectionView(
                modifier = Modifier,
                list = viewState.images
            ) { imageData ->
                actions.showDialog(true, imageData)
            }
        }
    }

    viewState.imageModel?.let {
        if (viewState.showDialog) {
            ImageDetailWarningDialog(
                imageDetail = it,
                onPositive = {
                    navigateToImageDetail(it)
                    actions.showDialog(false, null)
                },
                dismissDialog = {
                    actions.showDialog(false, null)
                }
            )
        }
    }
}
