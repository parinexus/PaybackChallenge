package com.pixabay.challenge.ui.imagedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.pixabay.challenge.R
import com.pixabay.challenge.common.openBrowser
import com.pixabay.challenge.ui.components.CustomImageView
import com.pixabay.challenge.ui.imagelist.TagChipView
import com.pixabay.challenge.ui.model.ImageUiModel

val DrawableId = SemanticsPropertyKey<Int>("DrawableResId")
var SemanticsPropertyReceiver.drawableId by DrawableId

@ExperimentalLayoutApi
@Composable
fun ImageInfoScreen(
    imageDetail: ImageUiModel,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier,
                        text = imageDetail.user,
                        style = MaterialTheme.typography.h6.copy(
                            color = MaterialTheme.colors.onPrimary
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_navigation),
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            FullImageCard(imageDetail.largeImageURL)

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_standard)))

            TagChipView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.spacing_standard)),
                tags = imageDetail.tags
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_standard)))

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                mainAxisAlignment = FlowMainAxisAlignment.Center,
                mainAxisSpacing = dimensionResource(id = R.dimen.spacing_standard),
                crossAxisSpacing = dimensionResource(id = R.dimen.spacing_standard)
            ) {
                IconLabelStack(
                    modifier = Modifier,
                    icon = R.drawable.ic_download,
                    text = imageDetail.downloads
                )
                IconLabelStack(
                    modifier = Modifier,
                    icon = R.drawable.ic_comment,
                    text = imageDetail.comments
                )
                IconLabelStack(
                    modifier = Modifier,
                    icon = R.drawable.ic_like,
                    text = imageDetail.likes
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))

            ImageSourceCredit(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.spacing_standard)))
        }
    }
}


@Composable
fun IconLabelStack(
    modifier: Modifier,
    icon: Int,
    text: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painterResource(id = icon),
            contentDescription = text,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.icon_size))
                .padding(dimensionResource(id = R.dimen.spacing_standard))
                .semantics { drawableId = icon },
            tint = MaterialTheme.colors.onBackground
        )
        Text(
            text = text,
            fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp,
            style = MaterialTheme.typography.overline,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
private fun FullImageCard(imagePath: String) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .wrapContentWidth()
            .height(dimensionResource(id = R.dimen.image_height))
            .padding(dimensionResource(id = R.dimen.padding_standard))
            .background(MaterialTheme.colors.onPrimary, shape = MaterialTheme.shapes.medium)
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_standard)))
        CustomImageView(context, imagePath, false, Modifier.fillMaxSize())
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_standard)))
    }
}

@Composable
private fun ImageSourceCredit(modifier: Modifier) {
    val context = LocalContext.current
    val annotatedString = buildAnnotatedString {
        append(stringResource(R.string.origin))
        pushStringAnnotation(
            tag = stringResource(R.string.origin),
            annotation = stringResource(R.string.source_website)
        )
        withStyle(style = SpanStyle(color = MaterialTheme.colors.onBackground)) {
            append(stringResource(R.string.source_website))
        }
    }
    ClickableText(
        modifier = modifier
            .background(color = MaterialTheme.colors.secondary)
            .padding(dimensionResource(id = R.dimen.padding_2xSmall)),
        text = annotatedString,
        style = TextStyle(
            color = MaterialTheme.colors.onBackground
        ),
        onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = context.getString(R.string.origin),
                start = offset,
                end = offset
            ).firstOrNull()?.let { url ->
                context.openBrowser(url.item)
            }
        }

    )
}
