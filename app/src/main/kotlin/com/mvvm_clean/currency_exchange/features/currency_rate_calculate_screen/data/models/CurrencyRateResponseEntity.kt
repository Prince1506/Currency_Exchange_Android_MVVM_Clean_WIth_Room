package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.models

import androidx.annotation.Keep
import com.mvvm_clean.currency_exchange.core.data.Error
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.models.CurrencyRateInfo

// Response for fact list API
@Keep
data class CurrencyRateResponseEntity(
    private var success: String? = null,
    private var terms: String? = null,
    private var privacy: String? = null,
    private var timestamp: Long? = null,
    private var source: String? = null,
    private var quotes: Map<String?, Double?>? = null,
    private var error: Error? = null
) {

    fun toFacts() = CurrencyRateInfo(
        0,
        success,
        terms,
        privacy,
        source,
        timestamp,
        quotes,
        error
    )
}
