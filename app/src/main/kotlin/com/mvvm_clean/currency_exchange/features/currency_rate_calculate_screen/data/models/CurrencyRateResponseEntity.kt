package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.models

import androidx.annotation.Keep
import androidx.room.Ignore
import com.mvvm_clean.currency_exchange.core.data.Error
import com.mvvm_clean.currency_exchange.core.domain.extension.isEmptyOrNull
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.models.CurrencyRateInfo

// Response for currency Rates list API
@Keep
data class CurrencyRateResponseEntity(
    private var success: String? = null,
    private var terms: String? = null,
    private var privacy: String? = null,
    private var timestamp: Long? = null,
    private var source: String,
    private var quotes: Map<String?, Double?>? = null,
    private var error: Error? = null,
    @Transient @Ignore
    private var isDataFromNetwork: Boolean = false
) {
    val sourceNotNull: String
        get() = if (String.isEmptyOrNull(source)) "-" else source!!

    fun toRates(source: String) = CurrencyRateInfo(
        success,
        terms,
        privacy,
        source,
        timestamp,
        quotes,
        error,
        true
    )
}
