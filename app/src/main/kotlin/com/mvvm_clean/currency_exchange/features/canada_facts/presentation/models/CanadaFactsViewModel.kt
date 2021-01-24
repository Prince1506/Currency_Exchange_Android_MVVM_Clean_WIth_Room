package com.mvvm_clean.currency_exchange.features.canada_facts.presentation.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mvvm_clean.currency_exchange.core.base.BaseViewModel
import com.mvvm_clean.currency_exchange.core.domain.exception.Failure
import com.mvvm_clean.currency_exchange.core.domain.extension.launchSilent
import com.mvvm_clean.currency_exchange.core.source.disk.DiskDataSource
import com.mvvm_clean.currency_exchange.features.canada_facts.data.CurrencyExchangeRequestEntity
import com.mvvm_clean.currency_exchange.features.canada_facts.data.repo.CanadaFactsInfo
import com.mvvm_clean.currency_exchange.features.canada_facts.data.repo.CurrencyListInfo
import com.mvvm_clean.currency_exchange.features.canada_facts.domain.use_cases.GetCanadaFactsInfo
import com.mvvm_clean.currency_exchange.features.canada_facts.domain.use_cases.GetCurrencyListInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

/**
 * View Model responsible for showing canada fact list on screen.
 * It will interact with both data as well as UI layer
 */
class CanadaFactsViewModel @Inject constructor(
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

    suspend fun getDataFromDisk() =
        launchSilent(coroutineContext, Job()) {
            diskDataSource.getAllCurrencyExchangeRates()?.get(0)
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
        diskDataSource.insertAllCurrencyExchangeRates(CanadaFactsInfo.empty)
    }


    fun handleFactList(canadaFactsInfo: CanadaFactsInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            diskDataSource.insertAllCurrencyExchangeRates(canadaFactsInfo)

        }

        isProgressLoading.value = false
        mutableCanadaLiveData.value = CanadaFactsModel(
            canadaFactsInfo.successNotNull,
            canadaFactsInfo.termsNotNull,
            canadaFactsInfo.privacyNotNull,
            canadaFactsInfo.sourceNotNull,
            canadaFactsInfo.timestampNotNull,
            canadaFactsInfo.quotesNotNull,
            canadaFactsInfo.error
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
