package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data

import androidx.annotation.Keep
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.repo.CurrencyListInfo

// Response for fact list API


@Keep
data class CurrencyListResponseEntity(
    private val success: String,
    private val terms: String,
    private val privacy: String,
    private val currencies: Map<String, String>
) {

    fun toFacts() = CurrencyListInfo(success, terms, privacy, currencies)
}
