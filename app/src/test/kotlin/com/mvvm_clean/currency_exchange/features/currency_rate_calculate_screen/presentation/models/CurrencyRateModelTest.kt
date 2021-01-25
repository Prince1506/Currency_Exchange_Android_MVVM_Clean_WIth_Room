package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.models

import androidx.lifecycle.MutableLiveData
import com.mvvm_clean.currency_exchange.AndroidTest
import com.mvvm_clean.currency_exchange.core.domain.functional.Either.Right
import com.mvvm_clean.currency_exchange.core.source.disk.DiskDataSource
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.models.CurrencyExchangeRequestEntity
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.models.CurrencyListInfo
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.models.CurrencyRateResponseEntity
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.repo.constants.IAPIConstants
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.models.CurrencyRateInfo
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.repo.CurrencyRateRepository
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.use_cases.GetCurrencyListInfo
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.use_cases.GetCurrencyRateInfo
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class CurrencyRateModelTest : AndroidTest() {

    private lateinit var currencyRatesViewModel: CurrencyRatesViewModel
    private lateinit var currencyRateInfo: CurrencyRateInfo
    private lateinit var currencyListInfo: CurrencyListInfo
    private val mutableCurrencyLiveData: MutableLiveData<CurrencyRateModel> = MutableLiveData()

    @MockK
    private lateinit var currencyRateResponse: Response<CurrencyRateResponseEntity>

    @MockK
    private lateinit var diskDataSource: DiskDataSource

    private lateinit var currencyRateResponseEntity: CurrencyRateResponseEntity
    private val success = "success"
    private val privacy = "privacy"
    private val terms = "terms"
    private val source = "source"
    private val timestamp = 1000L
    private val quotes = createDummyMapWithCurrencies()
    private val isDataFromNetwork = true

    private lateinit var currencyExchnageRequestEntity: CurrencyExchangeRequestEntity
    private lateinit var networkRepository: CurrencyRateRepository.Network

    @MockK
    private lateinit var currencyRateRepository: CurrencyRateRepository

    @MockK
    private lateinit var getCurrencyRateInfo: GetCurrencyRateInfo

    @MockK
    private lateinit var getCurrencyListInfo: GetCurrencyListInfo

    @Before
    fun setUp() {
        currencyExchnageRequestEntity = CurrencyExchangeRequestEntity(
            0,
            IAPIConstants.accessKeyVal,
            IAPIConstants.currenciesToShow,
            source,
            IAPIConstants.jsonSyntax
        )


        currencyRateInfo = CurrencyRateInfo(
            success,
            terms,
            privacy,
            source,
            timestamp,
            createDummyMapWithCurrenciesWithStrings()
        )

        currencyListInfo = CurrencyListInfo(
            success,
            terms,
            privacy,
            createDummyMapWithCurrencyList()
        )

        currencyRatesViewModel = CurrencyRatesViewModel(
            getCurrencyRateInfo,
            getCurrencyListInfo,
            diskDataSource
        )

        mutableCurrencyLiveData.value = CurrencyRateModel(
            success,
            terms,
            privacy,
            source,
            timestamp,
            quotes,
            null
        )
    }


    /**
     * Whenever there is a change inside API response live data should get updated.
     */
    @Test
    fun `loading currency list should update live data`() {

        // Assert
        coEvery { getCurrencyListInfo.run(any()) } returns Right(currencyListInfo)

        // Act
        runBlocking { currencyRatesViewModel.getCurrencyList(currencyExchnageRequestEntity.accessKey) }

        // Verify
        currencyRatesViewModel.getCurrencyLiveData().observeForever {
            it.let {
                it.currency.size shouldEqualTo currencyListInfo.currencies.size
                it.privacy shouldEqualTo currencyListInfo.privacy
                it.success shouldEqualTo currencyListInfo.success
                it.terms shouldEqualTo currencyListInfo.terms
            }
        }

    }

    /**
     * Whenever there is a change inside API response live data should get updated.
     */
    @Test
    fun `loading currency rates should update live data`() {

        // Assert
        coEvery { getCurrencyRateInfo.run(any()) } returns Right(currencyRateInfo)

        // Act
        runBlocking { currencyRatesViewModel.loadCurrencyRateList(currencyExchnageRequestEntity) }

        // Verify
        currencyRatesViewModel.getCurrencyRateLiveData().observeForever {
            it.let {
                it.quotes.size shouldEqualTo currencyRateInfo.quotes?.size
                it.privacy shouldEqualTo currencyRateInfo.privacy
                it.success shouldEqualTo currencyRateInfo.success
                it.terms shouldEqualTo currencyRateInfo.terms
            }
        }

    }


    private fun createDummyMapWithCurrencies(): Map<String, Double> {
        val dummyQuotes: MutableMap<String, Double> = java.util.HashMap()
        dummyQuotes?.set("USDGBP", 0.582139)
        dummyQuotes?.set("USDINR", 0.182139)
        dummyQuotes?.set("USDCAD", 0.782139)
        dummyQuotes?.set("USDPLN", 0.982139)
        dummyQuotes?.set("USDAOA", 0.482139)
        dummyQuotes?.set("USDANG", 0.982139)
        return dummyQuotes
    }

    private fun createDummyMapWithCurrenciesWithStrings(): Map<String?, Double?>? {
        val dummyQuotes: MutableMap<String?, Double?>? = java.util.HashMap()
        dummyQuotes?.set("USDGBP", 0.582139)
        dummyQuotes?.set("USDINR", 0.182139)
        dummyQuotes?.set("USDCAD", 0.782139)
        dummyQuotes?.set("USDPLN", 0.982139)
        dummyQuotes?.set("USDAOA", 0.482139)
        dummyQuotes?.set("USDANG", 0.982139)
        return dummyQuotes
    }

    private fun createDummyMapWithCurrencyList(): Map<String, String> {
        val dummyQuotes: MutableMap<String, String> = java.util.HashMap()
        dummyQuotes?.set("USDGBP", " 0.582139")
        dummyQuotes?.set("USDINR", "0.182139")
        return dummyQuotes
    }

}