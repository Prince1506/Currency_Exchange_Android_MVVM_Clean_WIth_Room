package com.mvvm_clean.currency_exchange.core.presentation.navigation

import android.content.Context
import android.view.View
import com.mvvm_clean.currency_exchange.features.canada_facts.presentation.ui.activities.CanadaFactListActivity
import com.mvvm_clean.currency_exchange.features.login.domain.Authenticator
import javax.inject.Inject
import javax.inject.Singleton


/**
 * This is central location to control screen flows(according to business) for complete app.
 */
@Singleton
class Navigator
@Inject constructor(private val authenticator: Authenticator) {


    fun showScreens(context: Context) {
        when (authenticator.userLoggedIn()) {
            true -> showCurrencyExchangeRateScreen(context)
        }
    }

    private fun showCurrencyExchangeRateScreen(context: Context) =
        context.startActivity(
            CanadaFactListActivity.callingIntent(context)
        )

    // Use It when you want to send parcellable object from one screen to another
    class Extras(val transitionSharedElement: View)
}


