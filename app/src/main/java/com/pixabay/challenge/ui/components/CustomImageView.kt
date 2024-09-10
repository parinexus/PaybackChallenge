package com.pixabay.challenge.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.pixabay.challenge.R

@Composable
fun CustomImageView(
    context: Context,
    imagePath: String,
    isCrossFadeEnable: Boolean,
    modifier: Modifier
) {
    val imageRequest = ImageRequest.Builder(context)
        .data(imagePath)
        .size(coil.size.Size.ORIGINAL)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)

    if (isCrossFadeEnable) {
        imageRequest.crossfade(isCrossFadeEnable)
        imageRequest.transformations(CircleCropTransformation())
    }

    val painter = rememberAsyncImagePainter(imageRequest.build(), context.imageLoader)

    if (painter.state is AsyncImagePainter.State.Loading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(dimensionResource(id = R.dimen.progress_indicator_size)),
                color = MaterialTheme.colors.onBackground
            )
        }
    } else {
        Image(
            painter = painter,
            contentDescription = imagePath,
            modifier = modifier,
            contentScale = ContentScale.FillBounds
        )
    }
}
