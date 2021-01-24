package com.mvvm_clean.currency_exchange.features.canada_facts.data.repo

import com.mvvm_clean.currency_exchange.core.domain.extension.empty

/**
 *  Canada fact list response is mapped to this pojo for handling business logic
 */

data class CurrencyListInfo(
    val success: String,
    val terms: String,
    val privacy: String,
    val currencies: Map<String, String>
) {

    companion object {
        val empty = CurrencyListInfo(String.empty(), String.empty(), String.empty(), emptyMap())
    }
}