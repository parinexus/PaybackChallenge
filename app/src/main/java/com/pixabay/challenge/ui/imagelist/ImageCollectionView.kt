package com.pixabay.challenge.ui.imagelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import com.pixabay.challenge.R
import com.pixabay.challenge.ui.model.ImageUiModel
import com.pixabay.challenge.utils.TestTag.IMAGE_COLLECTION_TAG

@ExperimentalLayoutApi
@ExperimentalMaterialApi
@Composable
fun ImageCollectionView(
    modifier: Modifier,
    list: List<ImageUiModel>,
    onCountryClick: (ImageUiModel) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_standard)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_small)),
        modifier = modifier.testTag(IMAGE_COLLECTION_TAG)
    ) {
        items(list.size, { itemKey ->
            itemKey.toString()
        }, itemContent = { itemIndex ->
            ImageThumbnailEntry(imageDetailUi = list[itemIndex]) {
                onCountryClick(list[itemIndex])
            }
        })
    }
}