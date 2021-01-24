package com.mvvm_clean.currency_exchange.features.canada_facts.domain.models

data class CurrencyExchangeDataRequestEntity(
    val accessKey: String,
    val currency: ArrayList<String>,
    val source: String,
    val format: Int
)