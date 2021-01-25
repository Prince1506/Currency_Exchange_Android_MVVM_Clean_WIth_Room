package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.repo

import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.models.CurrencyRateResponseEntity
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.models.CurrencyListResponseEntity
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.repo.constants.IAPIConstants
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.repo.constants.IAPIConstants.Companion.access_key
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.repo.constants.IAPIConstants.Companion.currencies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * All apis would trigger from here
 */
internal interface CurrencyRateApi :IAPIConstants{
    companion object {
        private const val FACTS = "/live"
        private const val CURRENCY_LIST = "/list"
    }

    @GET(FACTS)
    fun getRates(
        @Query(access_key) accessKey: String,
        @Query(currencies, encoded = true) currency: String,
        @Query(IAPIConstants.source) source: String,
        @Query(IAPIConstants.format) format: Int
    ): Call<CurrencyRateResponseEntity>

    @GET(CURRENCY_LIST)
    fun getCurrencyList(
        @Query(access_key) accessKey: String
    ): Call<CurrencyListResponseEntity>

}
