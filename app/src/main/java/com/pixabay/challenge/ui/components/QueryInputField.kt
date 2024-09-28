package com.pixabay.challenge.ui.components

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import com.pixabay.challenge.R
import com.pixabay.challenge.utils.UiConstants

@Composable
fun QueryInputField(
    onSearch: (searchQuery: String) -> Unit
) {
    val searchText = remember { mutableStateOf(UiConstants.DEFAULT_SEARCH_QUERY) }
    val context = LocalContext.current

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
                contentDescription = null,
                modifier = Modifier.clickable {
                    handleSearch(searchText.value, context, onSearch)
                },
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onBackground)
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                handleSearch(searchText.value, context, onSearch)
            }
        ),
        singleLine = true,
        maxLines = 1,
    )
}

private fun handleSearch(query: String, context: Context, onSearch: (searchQuery: String) -> Unit) {
    if (query.isNotBlank()) {
        onSearch(query)
    } else {
        Toast.makeText(context, context.getString(R.string.empty_query), Toast.LENGTH_SHORT).show()
    }
}
