package payback.pixabay.challenge.ui.imagelist

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
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import payback.pixabay.challenge.utils.TestTag.DETAIL_SCREEN_TAGS

@ExperimentalLayoutApi
@Composable
fun TagChipView(
    modifier: Modifier,
    tags: List<String>
) {
    FlowRow(
        modifier = modifier.testTag(DETAIL_SCREEN_TAGS),
        mainAxisAlignment = FlowMainAxisAlignment.Center,
        mainAxisSpacing = 10.dp,
        crossAxisSpacing = 10.dp
    ) {
        tags.forEach { tag ->
            Text(
                text = tag,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .background(color = MaterialTheme.colors.secondary, shape = CircleShape)
                    .padding(vertical = 3.dp, horizontal = 8.dp)
            )
        }
        Spacer(modifier.width(4.dp))
    }
}
