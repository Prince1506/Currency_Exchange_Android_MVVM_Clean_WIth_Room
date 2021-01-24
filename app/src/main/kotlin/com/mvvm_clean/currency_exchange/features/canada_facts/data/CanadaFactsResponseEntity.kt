package com.mvvm_clean.currency_exchange.features.canada_facts.data

import androidx.annotation.Keep
import com.mvvm_clean.currency_exchange.features.canada_facts.data.repo.CanadaFactsInfo

// Response for fact list API
@Keep
data class CanadaFactsResponseEntity(
    private val success: String,
    private val terms: String,
    private val privacy: String,
    private val timestamp: Long,
    private val source: String,
    private val quotes: String
    ) {

    fun toFacts() = CanadaFactsInfo(0, success, terms, privacy, source, timestamp, quotes)
}
