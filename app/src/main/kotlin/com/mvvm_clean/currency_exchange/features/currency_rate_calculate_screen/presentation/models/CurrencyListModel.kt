package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.models

import com.mvvm_clean.currency_exchange.core.base.KParcelable
import kotlinx.android.parcel.Parcelize

// Canada fact list model to be shown on UI
@Parcelize
data class CurrencyListModel(
    val success: String,
    val terms: String,
    val privacy: String,
    val currency: Map<String, String>
) : KParcelable