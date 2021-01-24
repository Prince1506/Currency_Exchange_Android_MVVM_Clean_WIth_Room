package com.mvvm_clean.currency_exchange.features.canada_facts.data

import androidx.annotation.Keep
import com.mvvm_clean.currency_exchange.core.base.KParcelable
import kotlinx.android.parcel.Parcelize

// Response for fact list API
@Keep
@Parcelize
data class QuotesResponseEntity(
    private val USDEUR: Double,
    private val USDGBP: Double,
    private val USDCAD: Double,
    private val USDPLN: Double,
    private val USDUSD: Double,
) : KParcelable {
    companion object {
        fun toEmpty() =
            QuotesResponseEntity(Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN)
    }
}