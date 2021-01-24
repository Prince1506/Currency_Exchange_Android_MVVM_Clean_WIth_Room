package com.mvvm_clean.currency_exchange.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.models.CurrencyRatesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyRatesViewModel::class)
    abstract fun bindsCanadaFactsViewModel(currencyRatesViewModel: CurrencyRatesViewModel): ViewModel

}