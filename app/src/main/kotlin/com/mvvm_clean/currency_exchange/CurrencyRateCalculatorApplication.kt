package com.mvvm_clean.currency_exchange

import com.mvvm_clean.currency_exchange.core.di.ApplicationComponent
import com.mvvm_clean.currency_exchange.core.di.ApplicationModule
import com.mvvm_clean.currency_exchange.core.di.DaggerApplicationComponent
import javax.inject.Inject

class CurrencyRateCalculatorApplication
@Inject constructor() : App() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

}
