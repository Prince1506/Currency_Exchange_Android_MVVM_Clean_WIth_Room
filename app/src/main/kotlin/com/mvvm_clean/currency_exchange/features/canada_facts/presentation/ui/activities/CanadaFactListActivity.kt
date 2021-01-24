package com.mvvm_clean.currency_exchange.features.canada_facts.presentation.ui.activities

import android.content.Context
import android.content.Intent
import com.mvvm_clean.currency_exchange.core.base.BaseActivity
import com.mvvm_clean.currency_exchange.features.canada_facts.presentation.ui.fragments.CanadaFactListFragment

// Activity responsible to trigger various fragments required on first screen
class CanadaFactListActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, CanadaFactListActivity::class.java)
    }

    override fun fragment() = CanadaFactListFragment()
}
