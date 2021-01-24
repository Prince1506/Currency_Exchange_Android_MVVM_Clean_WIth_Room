package com.mvvm_clean.currency_exchange.features.canada_facts.data


data class CurrencyExchangeRequestEntity(
    val id: Int = 0,
    val accessKey: String,
    val currency: String,
    val source: String,
    val format: Int
)