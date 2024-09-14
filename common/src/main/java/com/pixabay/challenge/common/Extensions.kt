package com.pixabay.challenge.common

import java.text.DecimalFormat

fun Long?.orZero() = this ?: 0
fun Int?.orZero() = this ?: 0

fun Long.format(): String = DecimalFormat("#,###.##").format(this)
