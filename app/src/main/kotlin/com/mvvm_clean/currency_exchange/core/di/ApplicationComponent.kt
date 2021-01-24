package com.mvvm_clean.currency_exchange.core.di

import com.mvvm_clean.currency_exchange.AboutCanadaApplication
import com.mvvm_clean.currency_exchange.core.presentation.navigation.AboutCanadaActivity
import com.mvvm_clean.currency_exchange.core.presentation.viewmodel.ViewModelModule
import com.mvvm_clean.currency_exchange.features.canada_facts.presentation.ui.fragments.CanadaFactListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: AboutCanadaApplication)
    fun inject(aboutCanadaActivity: AboutCanadaActivity)
    fun inject(canadaFactListFragment: CanadaFactListFragment)
}
