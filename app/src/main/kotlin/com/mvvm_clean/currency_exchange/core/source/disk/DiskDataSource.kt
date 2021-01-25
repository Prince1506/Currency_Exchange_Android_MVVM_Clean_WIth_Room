package com.mvvm_clean.currency_exchange.core.source.disk

import android.app.Application
import android.content.Context
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.models.CurrencyRateInfo
import javax.inject.Inject


class DiskDataSource
@Inject constructor(appContext: Context) {
    companion object {
        var database: Database? = null
    }

    init {
        database = Database.createInstance(appContext as Application)
    }

    fun insertAllCurrencyExchangeRates(currentTaskEntity: CurrencyRateInfo) =
        database?.CurrencyRateDao()?.insertAllCurrencyRates(currentTaskEntity)

    fun getCurrencyExchangeRateById(id: Int) =
        database?.CurrencyRateDao()?.getCurrencyRateById(id)
}
