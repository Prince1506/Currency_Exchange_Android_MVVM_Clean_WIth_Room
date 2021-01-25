package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.models

import com.mvvm_clean.currency_exchange.core.base.KParcelable
import com.mvvm_clean.currency_exchange.core.data.Error
import kotlinx.android.parcel.Parcelize

// currency Rates list model to be shown on UI
@Parcelize
data class CurrencyRateModel(
    val success: String,
    val terms: String,
    val privacy: String,
    val source: String,
    val timestamp: Long,
    val quotes: Map<String, Double>,
    val error: Error? = null
) : KParcelable