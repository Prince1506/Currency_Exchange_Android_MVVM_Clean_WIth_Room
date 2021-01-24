package com.mvvm_clean.currency_exchange.features.canada_facts.data.repo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mvvm_clean.currency_exchange.core.domain.extension.empty

/**
 *  Canada fact list response is mapped to this pojo for handling business logic
 */

const val tableName = "TABLE_CURRENCY_EXCHANGE_LIST"
@Entity(tableName = tableName)
data class CanadaFactsInfo(
    @PrimaryKey
    var id: Int = 0,
    var success: String,
    var terms: String,
    var privacy: String,
    var source: String,
    var timestamp: Long,
    var quotes:String
) {

    companion object {
        val empty = CanadaFactsInfo(0,String.empty(), String.empty(), String.empty(), String.empty(), Long.MIN_VALUE, "")
    }
}