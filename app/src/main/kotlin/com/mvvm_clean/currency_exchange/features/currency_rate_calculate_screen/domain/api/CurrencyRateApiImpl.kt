package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.api

import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.models.CurrencyRateResponseEntity
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.models.CurrencyListResponseEntity
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.repo.CurrencyRateApi
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * All apis would be handled here
 */
@Singleton
class CurrencyRateApiImpl
@Inject constructor(retrofit: Retrofit) : CurrencyRateApi {

    private val aboutCanadaApi by lazy { retrofit.create(CurrencyRateApi::class.java) }

    override fun getFacts(
        accessKey: String,
        currency: String,
        source: String,
        format: Int
    ): Call<CurrencyRateResponseEntity> {
        return aboutCanadaApi.getFacts(accessKey, currency, source, format)
    }

    override fun getCurrencyList(
        accessKey: String
    ): Call<CurrencyListResponseEntity> {
        return aboutCanadaApi.getCurrencyList(accessKey)
    }
}
