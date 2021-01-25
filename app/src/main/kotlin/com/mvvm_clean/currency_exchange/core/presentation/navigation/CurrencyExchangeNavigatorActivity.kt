package com.mvvm_clean.currency_exchange.core.presentation.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mvvm_clean.currency_exchange.CurrencyRateCalculatorApplication
import com.mvvm_clean.currency_exchange.core.di.ApplicationComponent
import javax.inject.Inject

/**
 * Main Activity.
 * This activity is responsible to launch various screens. It serve as one location from
 * where all other screens can be opened. All screens code to launch are controlled here using
 * navigator
 */
class CurrencyExchangeNavigatorActivity : AppCompatActivity() {

    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as CurrencyRateCalculatorApplication).appComponent
    }

    @Inject
    internal lateinit var navigator: Navigator

    // Override Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        navigator.showScreens(this)
    }
    //---

}
