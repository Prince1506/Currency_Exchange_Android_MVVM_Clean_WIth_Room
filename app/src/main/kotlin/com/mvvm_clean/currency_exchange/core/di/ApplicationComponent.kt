package com.mvvm_clean.currency_exchange.core.di

import com.mvvm_clean.currency_exchange.CurrencyRateCalculatorApplication
import com.mvvm_clean.currency_exchange.core.presentation.navigation.CurrencyExchangeNavigatorActivity
import com.mvvm_clean.currency_exchange.core.presentation.viewmodel.ViewModelModule
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.ui.fragments.CurrencyRateListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: CurrencyRateCalculatorApplication)
    fun inject(currencyExchangeNavigatorActivity: CurrencyExchangeNavigatorActivity)
    fun inject(currencyRateListFragment: CurrencyRateListFragment)
}
