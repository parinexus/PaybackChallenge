package com.pixabay.challenge.ui.imagelist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.pixabay.challenge.R
import com.pixabay.challenge.ui.components.CustomImageView
import com.pixabay.challenge.ui.model.ImageUiModel
import com.pixabay.challenge.utils.TestTag.IMAGE_ITEM_TAG

@ExperimentalLayoutApi
@ExperimentalMaterialApi
@Composable
fun ImageThumbnailEntry(imageDetailUi: ImageUiModel, onClick: () -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.lazy_item_height))
            .padding(vertical = dimensionResource(id = R.dimen.padding_small))
            .testTag(IMAGE_ITEM_TAG)
            .background(color = colorResource(id = R.color.accent_dark))
            .clickable { onClick.invoke() }
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_medium)))
    ) {

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = dimensionResource(id = R.dimen.padding_2xSmall)),
            contentAlignment = Alignment.Center
        ) {
            CustomImageView(
                context, imageDetailUi.previewURL, true,
                Modifier.size(dimensionResource(id = R.dimen.icon_size))
            )
        }

        Text(
            text = imageDetailUi.user,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.spacing_small)),
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.padding_2xSmall),
                    end = dimensionResource(id = R.dimen.padding_2xSmall)
                )
                .padding(
                    top = dimensionResource(id = R.dimen.spacing_small),
                    bottom = dimensionResource(id = R.dimen.spacing_small)
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TagChipView(
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.spacing_tiny)),
                tags = imageDetailUi.tags
            )
        }
    }
}