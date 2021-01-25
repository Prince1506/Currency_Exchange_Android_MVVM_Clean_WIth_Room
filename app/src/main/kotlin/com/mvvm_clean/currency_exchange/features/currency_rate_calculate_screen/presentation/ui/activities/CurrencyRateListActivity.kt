package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.ui.activities

import android.content.Context
import android.content.Intent
import com.mvvm_clean.currency_exchange.core.base.BaseActivity
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.ui.fragments.CurrencyRateListFragment

// Activity responsible to trigger various fragments required on first screen
public class CurrencyRateListActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, CurrencyRateListActivity::class.java)
    }

    override fun fragment() = CurrencyRateListFragment()
}
