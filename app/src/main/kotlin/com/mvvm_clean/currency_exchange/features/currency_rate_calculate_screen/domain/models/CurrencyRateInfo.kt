package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.mvvm_clean.currency_exchange.core.data.Error
import com.mvvm_clean.currency_exchange.core.domain.extension.empty
import com.mvvm_clean.currency_exchange.core.domain.extension.isEmptyOrNull

/**
 *  Currency Rate list response is mapped to this pojo for handling business logic
 */

const val tableName = "TABLE_CURRENCY_EXCHANGE_LIST"


@Entity(tableName = tableName)
data class CurrencyRateInfo @Ignore constructor(
    var success: String? = null,
    var terms: String? = null,
    var privacy: String? = null,

    @PrimaryKey
    var source: String = String.empty(), // Default value if API return null because primary key cannot be null
    var timestamp: Long? = null,
    var quotes: Map<String?, Double?>? = null,

    @Ignore var error: Error? = null,
    @Ignore var isDataFromNetwork: Boolean = false
) {

    val successNotNull: String
        get() = if (String.isEmptyOrNull(success)) "-" else success!!

    val termsNotNull: String
        get() = if (String.isEmptyOrNull(terms)) "-" else terms!!

    val privacyNotNull: String
        get() = if (String.isEmptyOrNull(privacy)) "-" else privacy!!

    val sourceNotNull: String
        get() = if (String.isEmptyOrNull(source)) "-" else source!!

    var timestampNotNull: Long = 0
        get() = if (Long.isEmptyOrNull(timestamp)) 0L else timestamp!!

    val quotesNotNull: Map<String, Double>
        get() = if (quotes == null) createDummyMapWithCurrencies() else createNotEmptyMap(quotes)


    private fun createDummyMapWithCurrencies(): Map<String, Double> {
        val dummyQuotes: MutableMap<String, Double> = java.util.HashMap()
        dummyQuotes["USDGBP"] = 0.582139
        dummyQuotes["USDINR"] = 0.182139
        dummyQuotes["USDCAD"] = 0.782139
        dummyQuotes["USDPLN"] = 0.982139
        dummyQuotes["USDAOA"] = 0.482139
        dummyQuotes["USDANG"] = 0.982139
        dummyQuotes["USDAMD"] = 0.78219
        dummyQuotes["USDEUR"] = 1.382139
        dummyQuotes["USDALL"] = 3.382139
        dummyQuotes["USDAFN"] = 1.382139
        dummyQuotes["USDAED"] = 2.382139
        dummyQuotes["USDAFN"] = 1.382139
        dummyQuotes["USDARS"] = 2.382139
        return dummyQuotes
    }


    private fun createNotEmptyMap(quotes: Map<String?, Double?>?): Map<String, Double> {
        val quotesNotNull: MutableMap<String, Double> = HashMap() //Object is containing String
        if (quotes != null) {
            for ((key, value) in quotes) {
                if (key != null && value != null) {
                    quotesNotNull[key] = value
                }
            }
        }
        return quotesNotNull
    }

    constructor(
        success: String?,
        terms: String?,
        privacy: String?,
        source: String,
        timestamp: Long?,
        quotes: Map<String?, Double?>?
    ) : this(
        success,
        terms,
        privacy,
        source,
        timestamp,
        quotes,
        null,
        false
    )

    companion object {
        val empty = CurrencyRateInfo(
            String.empty(),
            String.empty(),
            String.empty(),
            String.empty(),
            Long.MIN_VALUE,
            emptyMap(),
            null
        )
    }
}