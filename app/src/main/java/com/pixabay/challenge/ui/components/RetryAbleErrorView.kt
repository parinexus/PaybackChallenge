package com.pixabay.challenge.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.pixabay.challenge.R

@Composable
fun RetryAbleErrorView(message: String?, onTryAgain: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_xlarge))
            .clickable { onTryAgain.invoke() }
    ) {
        Text(
            text = message ?: stringResource(R.string.error_generic),
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.primary
        )
        Text(
            text = stringResource(R.string.retry_prompt),
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.primary
        )
    }
}
