package payback.pixabay.challenge.ui.imagelist

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import payback.pixabay.challenge.R
import payback.pixabay.challenge.ui.components.CustomImageView
import payback.pixabay.challenge.ui.model.ImageUiModel
import payback.pixabay.challenge.utils.TestTag.IMAGE_ITEM_TAG

@ExperimentalLayoutApi
@ExperimentalMaterialApi
@Composable
fun ImageThumbnailEntry(imageDetailUi: ImageUiModel, onClick: () -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(vertical = 8.dp)
            .testTag(IMAGE_ITEM_TAG)
            .background(color = colorResource(id = R.color.accent_dark))
            .clickable { onClick.invoke() }
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {

        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            CustomImageView(
                context, imageDetailUi.previewURL, true,
                Modifier.size(60.dp)
            )
        }


        Text(
            text = imageDetailUi.user,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
                .padding(top = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TagChipView(
                modifier = Modifier.padding(start = 8.dp),
                tags = imageDetailUi.tags
            )
        }
    }
}