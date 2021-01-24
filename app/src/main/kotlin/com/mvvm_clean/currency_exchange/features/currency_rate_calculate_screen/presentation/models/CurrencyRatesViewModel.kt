package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mvvm_clean.currency_exchange.core.base.BaseViewModel
import com.mvvm_clean.currency_exchange.core.domain.exception.Failure
import com.mvvm_clean.currency_exchange.core.source.disk.DiskDataSource
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.CurrencyExchangeRequestEntity
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.repo.CurrencyRateInfo
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.repo.CurrencyListInfo
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.use_cases.GetCanadaFactsInfo
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.use_cases.GetCurrencyListInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View Model responsible for showing canada fact list on screen.
 * It will interact with both data as well as UI layer
 */
class CurrencyRatesViewModel @Inject constructor(
    private val getCanadaFactsInfo: GetCanadaFactsInfo,
    private val getCurrencyListInfo: GetCurrencyListInfo,
    private val diskDataSource: DiskDataSource
) : BaseViewModel() {

    private val isProgressLoading = MutableLiveData<Boolean>()

    fun getIsLoading(): LiveData<Boolean?>? {
        return isProgressLoading
    }

    private val mutableCanadaLiveData: MutableLiveData<CanadaFactsModel> by lazy {
        MutableLiveData<CanadaFactsModel>()
    }

    fun getCurrencyLiveData(): LiveData<CurrencyListModel> {
        return mutableCurrencyLiveData
    }

    private val mutableCurrencyLiveData: MutableLiveData<CurrencyListModel> by lazy {
        MutableLiveData<CurrencyListModel>()
    }

    fun getCanadaFactLiveData(): LiveData<CanadaFactsModel> {
        return mutableCanadaLiveData
    }


    fun loadCanadaFacts(currencyExchangeRequestEntity: CurrencyExchangeRequestEntity) {
        isProgressLoading.value = true
        getCanadaFactsInfo(currencyExchangeRequestEntity) {
            it.fold(
                ::handleApiListFailure,
                ::handleFactList
            )
        }
    }

    fun getCurrencyList(accessKey: String) {
        isProgressLoading.value = true
        getCurrencyListInfo(accessKey) { it.fold(::handleApiListFailure, ::handleCurrencyList) }
    }


    private fun handleApiListFailure(failure: Failure) {
        super.handleFailure(failure)
        viewModelScope.launch(Dispatchers.IO) {
            insertIntoDb()
        }

        isProgressLoading.value = false
    }

    private suspend fun insertIntoDb() {
        diskDataSource.insertAllCurrencyExchangeRates(CurrencyRateInfo.empty)
    }


    fun handleFactList(currencyRateInfo: CurrencyRateInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            diskDataSource.insertAllCurrencyExchangeRates(currencyRateInfo)

        }

        isProgressLoading.value = false
        mutableCanadaLiveData.value = CanadaFactsModel(
            currencyRateInfo.successNotNull,
            currencyRateInfo.termsNotNull,
            currencyRateInfo.privacyNotNull,
            currencyRateInfo.sourceNotNull,
            currencyRateInfo.timestampNotNull,
            currencyRateInfo.quotesNotNull,
            currencyRateInfo.error
        )
    }

    private fun handleCurrencyList(currencyListInfo: CurrencyListInfo) {
        isProgressLoading.value = false
        mutableCurrencyLiveData.value = CurrencyListModel(
            currencyListInfo.success,
            currencyListInfo.terms,
            currencyListInfo.privacy,
            currencyListInfo.currencies
        )
    }
}
