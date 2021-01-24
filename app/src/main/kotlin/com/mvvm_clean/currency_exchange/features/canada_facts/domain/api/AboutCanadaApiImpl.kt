package com.mvvm_clean.currency_exchange.features.canada_facts.domain.api

import com.mvvm_clean.currency_exchange.features.canada_facts.data.CanadaFactsResponseEntity
import com.mvvm_clean.currency_exchange.features.canada_facts.data.CurrencyListResponseEntity
import com.mvvm_clean.currency_exchange.features.canada_facts.data.repo.AboutCanadaApi
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * All apis would be handled here
 */
@Singleton
class AboutCanadaApiImpl
@Inject constructor(retrofit: Retrofit) : AboutCanadaApi {

    private val aboutCanadaApi by lazy { retrofit.create(AboutCanadaApi::class.java) }

    override fun getFacts(
        accessKey: String,
        currency: String,
        source: String,
        format: Int
    ): Call<CanadaFactsResponseEntity> {
        return aboutCanadaApi.getFacts(accessKey, currency, source, format)
    }

    override fun getCurrencyList(
        accessKey: String
    ): Call<CurrencyListResponseEntity> {
        return aboutCanadaApi.getCurrencyList(accessKey)
    }
}
