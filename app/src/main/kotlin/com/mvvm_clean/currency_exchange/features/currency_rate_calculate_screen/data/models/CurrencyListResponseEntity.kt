package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.models

import androidx.annotation.Keep
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.models.CurrencyListInfo

// Response for currency list API
@Keep
data class CurrencyListResponseEntity(
    private val success: String,
    private val terms: String,
    private val privacy: String,
    private val currencies: Map<String, String>
) {

    fun toFacts() = CurrencyListInfo(success, terms, privacy, currencies)
}
