package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.ui.fragments

import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvvm_clean.currency_exchange.R
import com.mvvm_clean.currency_exchange.core.base.BaseFragment
import com.mvvm_clean.currency_exchange.core.domain.exception.Failure
import com.mvvm_clean.currency_exchange.core.domain.exception.Failure.NetworkConnection
import com.mvvm_clean.currency_exchange.core.domain.exception.Failure.ServerError
import com.mvvm_clean.currency_exchange.core.domain.extension.*
import com.mvvm_clean.currency_exchange.core.presentation.navigation.Navigator
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.CanadaFactsFailure
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.CurrencyExchangeRequestEntity
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.models.CanadaFactsModel
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.models.CurrencyRatesViewModel
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.models.CurrencyListModel
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.ui.adapters.CanadaFactListAdapter
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.ui.adapters.CurrencyListAdapter
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.ui.adapters.GridSpacingItemDecorationAdapter
import kotlinx.android.synthetic.main.fragment_canada_facts.*
import kotlinx.android.synthetic.main.layout_currency_list_drop_down.*
import java.lang.Double
import javax.inject.Inject

// Fragment responsible to show fact list
class CanadaFactListFragment : BaseFragment() {


    private val BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout"
    private lateinit var mCurrencyRatesViewModel: CurrencyRatesViewModel

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var currencyListAdapter: CurrencyListAdapter

    @Inject
    lateinit var canadaFactListAdapter: CanadaFactListAdapter

    private val accessKey = "9d7089948c7b2abf5f94c917434a3429"
    private val currency = ArrayList<String>()

    // Override Methods
    override fun layoutId() = R.layout.fragment_canada_facts
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        mCurrencyRatesViewModel = viewModel(viewModelFactory) {
            observe(getCanadaFactLiveData(), ::renderCanadaFactsList)
            failure(failure, ::handleFailure)
        }
        mCurrencyRatesViewModel = viewModel(viewModelFactory) {
            observe(getCurrencyLiveData(), ::renderCurrencyList)
            failure(failure, ::handleFailure)
        }

        mCurrencyRatesViewModel.getIsLoading()?.observe(this, object : Observer<Boolean?> {
            override fun onChanged(aBoolean: Boolean?) {
                if (aBoolean!!) {
                    showProgress()
                } else {
                    hideProgress()
                }
            }
        })

        // retain this fragment when activity is re-initialized
        retainInstance = true

        currency.add("EUR")
        currency.add("GBP")
        currency.add("INR")
        currency.add("YEN")
        currency.add("CAD")
        currency.add("PLN")
        currency.add("AOA")
        currency.add("ANG")
        currency.add("AMD")
        currency.add("ALL")
        currency.add("AFN")
        currency.add("AED")
        currency.add("ARS")
        currency.add("AUD")
        currency.add("AWG")
        currency.add("AZN")
        currency.add("BAM")
        currency.add("USD")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        mCurrencyRatesViewModel.getCurrencyList(accessKey)
    }

    /**
     * This is a method for Fragment.
     * You can do the same in onCreate or onRestoreInstanceState
     */
    override fun onViewStateRestored(@Nullable savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            val savedRecyclerLayoutState =
                savedInstanceState.getParcelable<Parcelable>(BUNDLE_RECYCLER_LAYOUT)
            rv_canadaFactList.layoutManager?.onRestoreInstanceState(savedRecyclerLayoutState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(
            BUNDLE_RECYCLER_LAYOUT,
            rv_canadaFactList.layoutManager?.onSaveInstanceState()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rv_canadaFactList.adapter = null
    }
    //---

    private fun initializeView() {

        ed_amount.hint = getString(R.string.enter_amount_hint)
        ed_amount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var isError = false
                ed_amount.setHintTextColor(resources.getColor(R.color.transparent_white))


                if (ed_amount.text.toString().trim { it <= ' ' }.isEmpty()) {
                    tv_wrong_amount.text = getString(R.string.blank_amount)
                    isError = true
                } else if (s.length > 12) {
                    tv_wrong_amount.text = getString(R.string.too_large_amount)
                    isError = true
                } else {
                    val inputDb = Double.parseDouble(s.toString())
                    isError = inputDb <= 0.0
                }


                if (isError) {
                    tv_wrong_amount.visible()
                } else {
                    tv_wrong_amount.gone()
                }

            }

            override fun afterTextChanged(s: Editable) {}
        })

        tv_currency.hint = getString(R.string.select_currency)
        rl_currency_parent.setOnClickListener {
            rl_currency_layout_parent.visible()
        }
        val spanCount = 2
        val gridLayoutManager = GridLayoutManager(activity, spanCount)
        val spacing = 16
        rv_canadaFactList.addItemDecoration(
            GridSpacingItemDecorationAdapter(
                spanCount,
                dpToPx(spacing),
                false
            )
        )

        rv_canadaFactList.layoutManager = gridLayoutManager

        rv_canadaFactList.adapter = canadaFactListAdapter

        rl_check_currency_rates_parent_btn.setOnClickListener {
            if (isFormValid())
                loadCanadaFactsList()
        }
        iv_currency_popup_cancel.setOnClickListener {
            rl_currency_layout_parent.gone()
        }

    }

    private fun isFormValid(): Boolean {
        var isFormValid = true
        if (tv_wrong_amount.isVisible()) isFormValid = false
        if (tv_currency.isEmpty()) isFormValid = false

        return isFormValid
    }

    private fun dpToPx(dp: Int): Int {
        try {
            val density = resources.displayMetrics.density
            return Math.round(dp.toFloat() * density)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    private fun loadCanadaFactsList() {
        val accessKey = "9d7089948c7b2abf5f94c917434a3429"
        val source = tv_currency.text.toString()
        val format = 1

        val currencyExchangeRequestEntity = CurrencyExchangeRequestEntity(
            0,
            accessKey,
            currency.joinToString(getString(R.string.comma)),
            source,
            format
        )
        showProgress()
        mCurrencyRatesViewModel.loadCanadaFacts(currencyExchangeRequestEntity)
    }

    private fun renderCanadaFactsList(canadaFactsModel: CanadaFactsModel?) {

        if (canadaFactsModel?.error == null) {
            val amount = Double.parseDouble(ed_amount.text.toString())

            val currencyWithAmountMap = canadaFactsModel?.quotes?.mapValues { it.value * amount }
            if (currencyWithAmountMap != null) {
                canadaFactListAdapter.collection = currencyWithAmountMap
            }
            rv_canadaFactList.visible()
        } else {
            notifyWithAction(canadaFactsModel.error.info)
        }
        hideProgress()
    }

    fun extractValue(entry: Map.Entry<String, kotlin.Double>) {

    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is ServerError -> renderFailure(R.string.failure_server_error)
            is CanadaFactsFailure.ListNotAvailable -> renderFailure(R.string.failure_canada_fact_list_unavailable)
            else -> renderFailure(R.string.failure_server_error)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        rv_canadaFactList.gone()
        hideProgress()
        notifyWithAction(getString(message))
    }


    private fun renderCurrencyList(currencyListModel: CurrencyListModel?) {
        rv_currencyList.layoutManager = LinearLayoutManager(activity)
        rv_currencyList.adapter = currencyListAdapter
        currencyListAdapter.clickListener = { currency, navigationExtras ->
            tv_currency.text = currency
            rl_currency_layout_parent.gone()
        }
        if (currencyListModel?.currency != null)
            currencyListAdapter.collection = currencyListModel.currency
    }
}
