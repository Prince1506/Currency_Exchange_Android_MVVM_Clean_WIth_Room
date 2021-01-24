package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.models

data class CurrencyExchangeDataRequestEntity(
    val accessKey: String,
    val currency: ArrayList<String>,
    val source: String,
    val format: Int
)