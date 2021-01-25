package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mvvm_clean.currency_exchange.core.base.BaseViewModel
import com.mvvm_clean.currency_exchange.core.domain.exception.Failure
import com.mvvm_clean.currency_exchange.core.source.disk.DiskDataSource
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.models.CurrencyExchangeRequestEntity
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.models.CurrencyListInfo
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.repo.constants.IAPIConstants
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.models.CurrencyRateInfo
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.use_cases.GetCurrencyListInfo
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.use_cases.GetCurrencyRateInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/**
 * View Model responsible for showing currency Rates list on screen.
 * It will interact with both data as well as UI layer
 */
class CurrencyRatesViewModel @Inject constructor(
    private val getCurrencyRateInfo: GetCurrencyRateInfo,
    private val getCurrencyListInfo: GetCurrencyListInfo,
    private val diskDataSource: DiskDataSource
) : BaseViewModel() {

    private val isProgressLoading = MutableLiveData<Boolean>()

    fun getIsLoading(): LiveData<Boolean?>? {
        return isProgressLoading
    }

    private val mutableCurrencyRateLiveData: MutableLiveData<CurrencyRateModel> by lazy {
        MutableLiveData<CurrencyRateModel>()
    }

    fun getCurrencyLiveData(): LiveData<CurrencyListModel> {
        return mutableCurrencyLiveData
    }

    private val mutableCurrencyLiveData: MutableLiveData<CurrencyListModel> by lazy {
        MutableLiveData<CurrencyListModel>()
    }

    fun getCurrencyRateLiveData(): LiveData<CurrencyRateModel> {
        return mutableCurrencyRateLiveData
    }


    fun loadCurrencyRateList(currencyExchangeRequestEntity: com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.models.CurrencyExchangeRequestEntity) {
        isProgressLoading.value = true
        getCurrencyRateInfo(currencyExchangeRequestEntity) {
            it.fold(
                ::handleApiListFailure,
                ::handleCurrencyRateList
            )
        }
    }

    fun getCurrencyList(accessKey: String) {
        isProgressLoading.value = true
        getCurrencyListInfo(accessKey) { it.fold(::handleApiListFailure, ::handleCurrencyList) }
    }


    private fun handleApiListFailure(failure: Failure) {
        super.handleFailure(failure)
        isProgressLoading.value = false
    }


    private fun handleCurrencyRateList(currencyRateInfo: CurrencyRateInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            if (currencyRateInfo.isDataFromNetwork) {
                currencyRateInfo.timestampNotNull = System.currentTimeMillis()
                currencyRateInfo.timestamp = System.currentTimeMillis()
                diskDataSource.insertAllCurrencyExchangeRates(currencyRateInfo)
            }

        }

        isProgressLoading.value = false
        mutableCurrencyRateLiveData.value = CurrencyRateModel(
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

    suspend fun getCurrencyRatePojoFromDb(source: String) =
        runBlocking(Dispatchers.IO) {

            val currencyExchangeRequestEntity =  CurrencyExchangeRequestEntity(
                0,
                IAPIConstants.accessKeyVal,
                IAPIConstants.currenciesToShow,
                source,
                IAPIConstants.jsonSyntax
            )

            val currencyExchangeResponseEntityInDb =
                diskDataSource.getCurrencyExchangeRateById(source)

            if (currencyExchangeResponseEntityInDb != null) {

                var timeDiffInSec =
                    (System.currentTimeMillis() - currencyExchangeResponseEntityInDb.timestampNotNull) / 1000

                currencyExchangeRequestEntity.isTimeExpired =
                    IAPIConstants.screenRefreshTimeInSec < timeDiffInSec
            }
            currencyExchangeRequestEntity
        }


}
