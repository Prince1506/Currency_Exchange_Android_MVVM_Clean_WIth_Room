package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.repo

import com.mvvm_clean.currency_exchange.UnitTest
import com.mvvm_clean.currency_exchange.core.domain.functional.Either.Right
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.models.CurrencyExchangeRequestEntity
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.repo.constants.IAPIConstants
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.models.CurrencyRateInfo
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.repo.CurrencyRateRepository
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.use_cases.GetCurrencyRateInfo
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCurrencyRateInfoTest : UnitTest() {

    private lateinit var currencyExchnageRequestEntity: CurrencyExchangeRequestEntity
    private lateinit var getCurrencyRateInfo: GetCurrencyRateInfo
    private lateinit var currencyRateInfo: CurrencyRateInfo
    private val success = "success"
    private val privacy = "privacy"
    private val terms = "terms"
    private val source = "source"
    private val timestamp = 1000L
    private val quotes = createDummyMapWithCurrencies()

    @MockK
    private lateinit var currencyRateRepository: CurrencyRateRepository

    @Before
    fun setUp() {

        getCurrencyRateInfo = GetCurrencyRateInfo(currencyRateRepository)

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
            quotes
        )
        every { currencyRateRepository.getRates(currencyExchnageRequestEntity) } returns Right(
            CurrencyRateInfo.empty
        )
    }

    /**
     * Check that API method is called once to get currency Rates list
     */
    @Test
    fun `should get data from repository`() {

        // Act
        runBlocking { getCurrencyRateInfo.run(currencyExchnageRequestEntity) }

        // Verify
        verify(exactly = 1) { currencyRateRepository.getRates(currencyExchnageRequestEntity) }
    }

    private fun createDummyMapWithCurrencies(): Map<String?, Double?>? {
        val dummyQuotes: MutableMap<String?, Double?>? = java.util.HashMap()
        dummyQuotes?.set("USDGBP", 0.582139)
        dummyQuotes?.set("USDINR", 0.182139)
        dummyQuotes?.set("USDCAD", 0.782139)
        dummyQuotes?.set("USDPLN", 0.982139)
        dummyQuotes?.set("USDAOA", 0.482139)
        dummyQuotes?.set("USDANG", 0.982139)
        return dummyQuotes
    }

}
