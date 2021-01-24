package com.mvvm_clean.currency_exchange.core.domain.extension

fun String.Companion.empty() = ""

fun String.Companion.isEmptyOrNull(mString: String?): Boolean =
    mString == null || mString.isEmpty()