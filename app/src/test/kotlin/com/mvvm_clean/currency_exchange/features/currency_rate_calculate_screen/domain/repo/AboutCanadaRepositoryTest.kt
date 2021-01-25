package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.repo

import com.mvvm_clean.currency_exchange.UnitTest
import com.mvvm_clean.currency_exchange.core.data.NetworkHandler
import com.mvvm_clean.currency_exchange.core.domain.exception.Failure.NetworkConnection
import com.mvvm_clean.currency_exchange.core.domain.exception.Failure.ServerError
import com.mvvm_clean.currency_exchange.core.domain.functional.Either
import com.mvvm_clean.currency_exchange.core.domain.functional.Either.Right
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.models.CurrencyRateResponseEntity
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.models.CurrencyRateInfo
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

class AboutCanadaRepositoryTest : UnitTest() {

    private lateinit var networkRepository: AboutCanadaRepository.Network

    private val TITLE_LBL = "title"
    private val DESCRIPTION_LBL = "description"
    private val HREF_LBL = "href"

    @MockK
    private lateinit var networkHandler: NetworkHandler

    @MockK
    private lateinit var service: CurrencyRateApiImpl
    private lateinit var `currencyRate  ResponseEntity`: CurrencyRateResponseEntity

    @MockK
    private lateinit var `currencyRat eResponseCall`: Call<CurrencyRateResponseEntity>

    @MockK
    private lateinit var currencyRateResponse: Response<CurrencyRateResponseEntity>

    @Before
    fun setUp() {
        networkRepository = AboutCanadaRepository.Network(networkHandler, service)
        `currencyRate  ResponseEntity` = CurrencyRateResponseEntity(
            TITLE_LBL,
            listOf(
                Quotes(
                    TITLE_LBL,
                    DESCRIPTION_LBL,
                    HREF_LBL
                )
            )
        )

    }

    /**
     * If API succeeded and we get response as null. Then it should return empty object
     */
    @Test
    fun `should return empty POJO by default`() {
        // Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { currencyRateResponse.body() } returns null
        every { currencyRateResponse.isSuccessful } returns true
        every { `currencyRat eResponseCall`.execute() } returns currencyRateResponse
        every { service.getFacts() } returns `currencyRat eResponseCall`

        // Act
        val canadaFacts = networkRepository.getFacts()

        //Verify
        canadaFacts shouldEqual Right(CurrencyRateInfo.empty)
        verify(exactly = 1) { service.getFacts() }
    }

    /**
     * Case when API succeeded and we get proper response from API then same should be returned by our method too.
     */
    @Test
    fun `should get canada facts list from service`() {
        // Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { currencyRateResponse.body() } returns `currencyRate  ResponseEntity`
        every { currencyRateResponse.isSuccessful } returns true
        every { `currencyRat eResponseCall`.execute() } returns currencyRateResponse
        every { service.getFacts() } returns `currencyRat eResponseCall`

        // Act
        val canadaFacts = networkRepository.getFacts()

        // Verify
        canadaFacts shouldEqual Right(`currencyRate  ResponseEntity`.toFacts())
        verify(exactly = 1) { service.getFacts() }
    }

    /**
     * Check if API fails due to no network app should show no internet message
     */
    @Test
    fun `canada facts service should return network failure when no connection`() {
        // Assert
        every { networkHandler.isNetworkAvailable() } returns false

        // Act
        val canadaFacts = networkRepository.getFacts()

        // Verify
        canadaFacts shouldBeInstanceOf Either::class.java
        canadaFacts.isLeft shouldEqual true
        canadaFacts.fold(
            { failure -> failure shouldBeInstanceOf NetworkConnection::class.java },
            {})
        verify { service wasNot Called }
    }

    /**
     * Check if API fails due to server error app should should show server error popup
     */
    @Test
    fun `canada facts service should return server error if no successful response`() {
        // Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { currencyRateResponse.isSuccessful } returns false
        every { `currencyRat eResponseCall`.execute() } returns currencyRateResponse
        every { service.getFacts() } returns `currencyRat eResponseCall`

        // Act
        val canadaFacts = networkRepository.getFacts()

        // Verify
        canadaFacts shouldBeInstanceOf Either::class.java
        canadaFacts.isLeft shouldEqual true
        canadaFacts.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    /**
     * Check if API fails due to any unhandled exception app should not get crash
     */
    @Test
    fun `fact request should catch exceptions`() {
        // Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { `currencyRat eResponseCall`.execute() } returns currencyRateResponse
        every { service.getFacts() } returns `currencyRat eResponseCall`

        // Act
        val canadaFacts = networkRepository.getFacts()

        // Verify
        canadaFacts shouldBeInstanceOf Either::class.java
        canadaFacts.isLeft shouldEqual true
        canadaFacts.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }
}