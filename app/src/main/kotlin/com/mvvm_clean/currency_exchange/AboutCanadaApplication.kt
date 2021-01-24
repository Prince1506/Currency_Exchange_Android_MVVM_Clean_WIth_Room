package com.mvvm_clean.currency_exchange

import android.app.Application
import com.mvvm_clean.currency_exchange.core.di.ApplicationComponent
import com.mvvm_clean.currency_exchange.core.di.ApplicationModule
import com.mvvm_clean.currency_exchange.core.di.DaggerApplicationComponent
import javax.inject.Inject

class AboutCanadaApplication
@Inject constructor(): App() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

}
