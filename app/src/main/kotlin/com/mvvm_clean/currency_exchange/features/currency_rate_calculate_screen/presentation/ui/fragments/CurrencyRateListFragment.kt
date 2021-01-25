package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.ui.fragments

import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
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
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.repo.constants.IAPIConstants.Companion.accessKeyVal
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.models.CanadaFactsModel
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.models.CurrencyListModel
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.models.CurrencyRatesViewModel
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.ui.adapters.CurrencyListAdapter
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.ui.adapters.CurrencyRateListAdapter
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.ui.adapters.GridSpacingItemDecorationAdapter
import kotlinx.android.synthetic.main.fragment_currency_rate_calc.*
import kotlinx.android.synthetic.main.layout_currency_list_drop_down.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Double
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


// Fragment responsible to show fact list
class CurrencyRateListFragment : BaseFragment(), CoroutineScope {

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout"
    private lateinit var mCurrencyRatesViewModel: CurrencyRatesViewModel

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var currencyListAdapter: CurrencyListAdapter

    @Inject
    lateinit var currencyRateListAdapter: CurrencyRateListAdapter


    // Override Methods
    override fun layoutId() = R.layout.fragment_currency_rate_calc
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        mCurrencyRatesViewModel = viewModel(viewModelFactory) {
            observe(getCanadaFactLiveData(), ::renderCurrencyRateList)
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        mCurrencyRatesViewModel.getCurrencyList(accessKeyVal)
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
            rv_currencyRateList.layoutManager?.onRestoreInstanceState(savedRecyclerLayoutState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(
            BUNDLE_RECYCLER_LAYOUT,
            rv_currencyRateList.layoutManager?.onSaveInstanceState()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rv_currencyRateList.adapter = null
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
                var isError: Boolean
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
        rv_currencyRateList.addItemDecoration(
            GridSpacingItemDecorationAdapter(
                spanCount,
                dpToPx(spacing),
                false
            )
        )

        rv_currencyRateList.layoutManager = gridLayoutManager

        rv_currencyRateList.adapter = currencyRateListAdapter

        rl_check_currency_rates_parent_btn.setOnClickListener {
            if (isFormValid()) {
                launch {
                    loadCurrencyRateList()
                }
            }

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

    private suspend fun loadCurrencyRateList() {
        val source = tv_currency.text.toString()

        val currencyRatePojoFromDb = mCurrencyRatesViewModel.getCurrencyRatePojoFromDb(source)
        showProgress()
        mCurrencyRatesViewModel.loadCurrencyRateList(currencyRatePojoFromDb)

    }

    private fun renderCurrencyRateList(currencyRateModel: CanadaFactsModel?) {

        if (currencyRateModel?.error != null) {
            showAlertDialog(
                getString(R.string.dummy_currency_list_title),
                getString(R.string.showing_dummy_currencies)
            )
            notifyWithAction(currencyRateModel.error.info)
        }
        val amount = Double.parseDouble(ed_amount.text.toString())

        val currencyWithAmountMap = currencyRateModel?.quotes?.mapValues { it.value * amount }
        if (currencyWithAmountMap != null) {
            currencyRateListAdapter.collection = currencyWithAmountMap
        }
        rv_currencyRateList.visible()
        hideProgress()
    }


    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is ServerError -> renderFailure(R.string.failure_server_error)
            else -> renderFailure(R.string.failure_server_error)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        rv_currencyRateList.gone()
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

    private fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(context!!)
            .setTitle(title)
            .setMessage(message)
            .show()
    }
}
