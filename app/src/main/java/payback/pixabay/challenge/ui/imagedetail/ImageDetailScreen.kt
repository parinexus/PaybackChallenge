package payback.pixabay.challenge.ui.imagedetail

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import payback.pixabay.challenge.R
import payback.pixabay.challenge.common.openBrowser
import payback.pixabay.challenge.ui.components.CustomImageView
import payback.pixabay.challenge.ui.imagelist.TagChipView
import payback.pixabay.challenge.ui.model.ImageUiModel
import payback.pixabay.challenge.utils.TestTag.COMMENT_TAG
import payback.pixabay.challenge.utils.TestTag.DOWNLOAD_TAG
import payback.pixabay.challenge.utils.TestTag.LARGE_IMAGE_TAG
import payback.pixabay.challenge.utils.TestTag.LIKE_TAG
import payback.pixabay.challenge.utils.TestTag.SOURCE_CREDIT_TAG
import payback.pixabay.challenge.utils.TestTag.IMAGE_DETAIL_TAG
import payback.pixabay.challenge.utils.TestTag.USERNAME_TAG

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
                        modifier = Modifier.testTag(USERNAME_TAG),
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
                .testTag(IMAGE_DETAIL_TAG)
        ) {
                FullImageCard(imageDetail.largeImageURL)

                Spacer(modifier = Modifier.height(16.dp))

                TagChipView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    tags = imageDetail.tags
                )

                Spacer(modifier = Modifier.height(16.dp))

                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues),
                    mainAxisAlignment = FlowMainAxisAlignment.Center,
                    mainAxisSpacing = 12.dp,
                    crossAxisSpacing = 12.dp
                ) {
                    IconLabelStack(
                        modifier = Modifier.testTag(DOWNLOAD_TAG),
                        icon = R.drawable.ic_download,
                        text = imageDetail.downloads
                    )
                    IconLabelStack(
                        modifier = Modifier.testTag(COMMENT_TAG),
                        icon = R.drawable.ic_comment,
                        text = imageDetail.comments
                    )
                    IconLabelStack(
                        modifier = Modifier.testTag(LIKE_TAG),
                        icon = R.drawable.ic_like,
                        text = imageDetail.likes
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                ImageSourceCredit(modifier = Modifier.padding(horizontal = 16.dp))

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
                .size(54.dp)
                .padding(12.dp)
                .semantics { drawableId = icon },
            tint = MaterialTheme.colors.onBackground
        )
        Text(
            text = text,
            fontSize = 18.sp,
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
            .height(300.dp)
            .padding(4.dp)
            .background(MaterialTheme.colors.onPrimary, shape = MaterialTheme.shapes.medium)
            .testTag(LARGE_IMAGE_TAG)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        CustomImageView(context, imagePath, false, Modifier.fillMaxSize())
        Spacer(modifier = Modifier.height(16.dp))

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
            .padding(4.dp)
            .testTag(SOURCE_CREDIT_TAG),
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
