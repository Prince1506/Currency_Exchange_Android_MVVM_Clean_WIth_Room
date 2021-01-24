package com.mvvm_clean.currency_exchange.core.source.disk

import android.app.Application
import android.content.Context
import com.mvvm_clean.currency_exchange.AboutCanadaApplication
import com.mvvm_clean.currency_exchange.features.canada_facts.data.repo.CanadaFactsInfo
import javax.inject.Inject


class DiskDataSource
@Inject constructor(appContext: Context) {
    companion object {
        var database: Database? = null
    }

    init {
        database = Database.createInstance(appContext as Application)
    }

     fun insertAllCurrencyExchangeRates(currentTaskEntity: CanadaFactsInfo) =
        database?.TaskListDao()?.insertAllCurrencyExchangeRates(currentTaskEntity)

    suspend fun getAllCurrencyExchangeRates() =
        database?.TaskListDao()?.getAllCurrencyExchangeRates()


     fun getCurrencyExchangeRateById(id: Int) =
        database?.TaskListDao()?.getCurrencyExchangeRateById(id)
}
