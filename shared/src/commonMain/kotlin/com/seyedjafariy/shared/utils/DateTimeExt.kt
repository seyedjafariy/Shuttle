package com.seyedjafariy.shared.utils

import kotlinx.datetime.toLocalDateTime

fun String.formatAndParseToLocalDateTime() =
    substring(0, indexOf('+').takeIf { it != -1 } ?: lastIndex).toLocalDateTime()