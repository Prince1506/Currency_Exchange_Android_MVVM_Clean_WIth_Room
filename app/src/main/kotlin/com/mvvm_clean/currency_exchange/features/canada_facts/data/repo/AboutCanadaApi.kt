package com.mvvm_clean.currency_exchange.features.canada_facts.data.repo

import com.mvvm_clean.currency_exchange.features.canada_facts.data.CanadaFactsResponseEntity
import com.mvvm_clean.currency_exchange.features.canada_facts.data.CurrencyListResponseEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * All apis would trigger from here
 */
internal interface AboutCanadaApi {
    companion object {
        private const val FACTS = "/api/live"
        private const val CURRENCY_LIST = "/list"
    }

    @GET(FACTS)
    fun getFacts(
        @Query("access_key") accessKey: String,
        @Query("currencies", encoded = true) currency: String,
        @Query("source") source: String,
        @Query("format") format: Int): Call<CanadaFactsResponseEntity>

    @GET(CURRENCY_LIST)
    fun getCurrencyList(
        @Query("access_key") accessKey: String): Call<CurrencyListResponseEntity>

}
