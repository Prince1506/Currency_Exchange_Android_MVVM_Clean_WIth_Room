package com.mvvm_clean.currency_exchange.core.data

import com.mvvm_clean.currency_exchange.core.base.KParcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Error(
    val code: String,
    val info: String
) : KParcelable