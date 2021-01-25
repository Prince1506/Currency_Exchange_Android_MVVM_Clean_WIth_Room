package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.models


/**
 * Pojo to make a request to get currency rate for current currency
 */
data class CurrencyExchangeRequestEntity(
    val id: Int = 0,
    val accessKey: String,
    val currency: String,
    val source: String,
    val format: Int,
    var isTimeExpired: Boolean = true
)