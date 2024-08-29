package payback.pixabay.challenge.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import payback.pixabay.challenge.R

@Composable
fun QueryInputField(
    onSearch: (searchQuery: String) -> Unit
) {
    val searchText = remember { mutableStateOf("fruits") }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = searchText.value,
        onValueChange = {
            searchText.value = it
        },
        trailingIcon = {
            Image(
                painter = painterResource(R.drawable.ic_search),
                contentScale = ContentScale.FillBounds,
                contentDescription = "",
                modifier = Modifier.clickable {
                    onSearch(searchText.value)
                },
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onBackground)
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(searchText.value)
            }
        )

    )
}
