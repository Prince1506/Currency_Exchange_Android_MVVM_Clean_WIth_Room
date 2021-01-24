package com.mvvm_clean.currency_exchange

import androidx.multidex.MultiDexApplication
import com.mvvm_clean.currency_exchange.core.di.ApplicationComponent
import com.mvvm_clean.currency_exchange.core.di.ApplicationModule
import com.mvvm_clean.currency_exchange.core.di.DaggerApplicationComponent
import leakcanary.LeakCanary

/**
 * If you app minSdk is lower than 20 then to enable multidex  (app can use above 64k methods)
 * u need to extend multidex application class else no need.
 */
open class App : MultiDexApplication() {
    companion object {
        lateinit var dependencyGraph: ApplicationComponent
    }
    private fun initLibraries() {
        initializeDagger()
    }

    private fun initializeDagger() {
        dependencyGraph = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }


    fun getApplicationComponent() = dependencyGraph
}