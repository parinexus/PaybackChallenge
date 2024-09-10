package com.pixabay.challenge.ui.imagelist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.pixabay.challenge.R
import com.pixabay.challenge.utils.TestTag.DETAIL_SCREEN_TAGS

@ExperimentalLayoutApi
@Composable
fun TagChipView(
    modifier: Modifier,
    tags: List<String>
) {
    FlowRow(
        modifier = modifier.testTag(DETAIL_SCREEN_TAGS),
        mainAxisAlignment = FlowMainAxisAlignment.Center,
        mainAxisSpacing = dimensionResource(id = R.dimen.spacing_small),
        crossAxisSpacing = dimensionResource(id = R.dimen.spacing_small)
    ) {
        tags.forEach { tag ->
            Text(
                text = tag,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .background(color = MaterialTheme.colors.secondary, shape = CircleShape)
                    .padding(
                        vertical = dimensionResource(id = R.dimen.padding_vertical_chip),
                        horizontal = dimensionResource(id = R.dimen.padding_horizontal_chip)
                    )
            )
        }
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_tiny)))
    }
}
