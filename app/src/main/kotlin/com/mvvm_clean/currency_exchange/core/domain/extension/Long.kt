package com.mvvm_clean.currency_exchange.core.domain.extension


fun Long.Companion.isEmptyOrNull(mLong: Long?): Boolean =
    mLong == null