package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.repo

import com.mvvm_clean.currency_exchange.UnitTest
import com.mvvm_clean.currency_exchange.core.data.NetworkHandler
import com.mvvm_clean.currency_exchange.core.domain.exception.Failure.NetworkConnection
import com.mvvm_clean.currency_exchange.core.domain.exception.Failure.ServerError
import com.mvvm_clean.currency_exchange.core.domain.functional.Either
import com.mvvm_clean.currency_exchange.core.domain.functional.Either.Right
import com.mvvm_clean.currency_exchange.core.source.disk.DiskDataSource
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.models.CurrencyExchangeRequestEntity
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.models.CurrencyRateResponseEntity
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.repo.constants.IAPIConstants
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.api.CurrencyRateApiImpl
import io.mockk.Called
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class CurrencyRateRepositoryTest : UnitTest() {


    @MockK
    private lateinit var networkHandler: NetworkHandler

    @MockK
    private lateinit var service: CurrencyRateApiImpl

    @MockK
    private lateinit var currencyRateResponseCall: Call<CurrencyRateResponseEntity>


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


    @Before
    fun setUp() {
        currencyExchnageRequestEntity = CurrencyExchangeRequestEntity(
            0,
            IAPIConstants.accessKeyVal,
            IAPIConstants.currenciesToShow,
            source,
            IAPIConstants.jsonSyntax
        )

        networkRepository = CurrencyRateRepository.Network(networkHandler, diskDataSource, service)

        currencyRateResponseEntity = CurrencyRateResponseEntity(
            success,
            terms,
            privacy,
            timestamp,
            source,
            quotes,
            null,
            isDataFromNetwork
        )

    }


    /**
     * Case when API succeeded and we get proper response from API then same should be returned by our method too.
     */
    @Test
    fun `should get currency Rates list from service`() {
        // Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { currencyRateResponse.body() } returns currencyRateResponseEntity
        every { diskDataSource.getCurrencyExchangeRateById(source) } returns currencyRateResponseEntity.toRates(source)
        every { currencyRateResponse.isSuccessful } returns true
        every { currencyRateResponseCall.execute() } returns currencyRateResponse
        every {
            service.getRates(
                currencyExchnageRequestEntity.accessKey,
                currencyExchnageRequestEntity.currency,
                currencyExchnageRequestEntity.source,
                currencyExchnageRequestEntity.format
            )
        } returns currencyRateResponseCall

        // Act
        val currencyRates = networkRepository.getRates(
            currencyExchnageRequestEntity

        )

        // Verify
        currencyRates shouldEqual Right(
            currencyRateResponseEntity.toRates(
                source
            )
        )
        verify(exactly = 1) { service.getRates( currencyExchnageRequestEntity.accessKey,
            currencyExchnageRequestEntity.currency,
            currencyExchnageRequestEntity.source,
            currencyExchnageRequestEntity.format) }
    }

    /**
     * Check if API fails due to no network app should show no internet message
     */
    @Test
    fun `currency Rates service should return network failure when no connection`() {
        // Assert
        every { networkHandler.isNetworkAvailable() } returns false
        every { diskDataSource.getCurrencyExchangeRateById(source) } returns currencyRateResponseEntity.toRates(source)

        // Act
        val currencyRates = networkRepository.getRates(currencyExchnageRequestEntity)

        // Verify
        currencyRates shouldBeInstanceOf Either::class.java
        currencyRates.isLeft shouldEqual true
        currencyRates.fold(
            { failure -> failure shouldBeInstanceOf NetworkConnection::class.java },
            {})
        verify { service wasNot Called }
    }

    /**
     * Check if API fails due to server error app should should show server error popup
     */
    @Test
    fun `currency Rates service should return server error if no successful response`() {
        // Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { diskDataSource.getCurrencyExchangeRateById(source) } returns currencyRateResponseEntity.toRates(source)
        every { currencyRateResponse.isSuccessful } returns false
        every { currencyRateResponseCall.execute() } returns currencyRateResponse
        every { service.getRates( currencyExchnageRequestEntity.accessKey,
            currencyExchnageRequestEntity.currency,
            currencyExchnageRequestEntity.source,
            currencyExchnageRequestEntity.format) } returns currencyRateResponseCall

        // Act
        val currencyRates = networkRepository.getRates(currencyExchnageRequestEntity)

        // Verify
        currencyRates shouldBeInstanceOf Either::class.java
        currencyRates.isLeft shouldEqual true
        currencyRates.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    /**
     * Check if API fails due to any unhandled exception app should not get crash
     */
    @Test
    fun `currency Rates request should catch exceptions`() {
        // Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { diskDataSource.getCurrencyExchangeRateById(source) } returns currencyRateResponseEntity.toRates(source)
        every { currencyRateResponseCall.execute() } returns currencyRateResponse
        every { service.getRates( currencyExchnageRequestEntity.accessKey,
            currencyExchnageRequestEntity.currency,
            currencyExchnageRequestEntity.source,
            currencyExchnageRequestEntity.format) } returns currencyRateResponseCall

        // Act
        val currencyRates = networkRepository.getRates(currencyExchnageRequestEntity)

        // Verify
        currencyRates shouldBeInstanceOf Either::class.java
        currencyRates.isLeft shouldEqual true
        currencyRates.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
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