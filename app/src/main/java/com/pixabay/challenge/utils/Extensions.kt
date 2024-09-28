package com.pixabay.challenge.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openBrowser(url: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}