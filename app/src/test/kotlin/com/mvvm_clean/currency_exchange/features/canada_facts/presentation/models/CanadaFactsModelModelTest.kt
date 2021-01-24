package com.mvvm_clean.currency_exchange.features.canada_facts.presentation.models

import androidx.lifecycle.MutableLiveData
import com.mvvm_clean.currency_exchange.AndroidTest
import com.mvvm_clean.currency_exchange.core.domain.functional.Either.Right
import com.mvvm_clean.currency_exchange.features.canada_facts.data.repo.CurrencyRateInfo
import com.mvvm_clean.currency_exchange.features.canada_facts.domain.use_cases.GetCanadaFactsInfo
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class CanadaFactsModelModelTest : AndroidTest() {

    private lateinit var currencyRatesViewModel: CurrencyRatesViewModel
    private lateinit var currencyRateInfo: CurrencyRateInfo
    private val TITLE_LBL = "title"
    private val DESCRIPTION_LBL = "description"
    private val HREF_LBL = "href"
    private val mutableCanadaLiveData: MutableLiveData<CanadaFactsModel> = MutableLiveData()
    private lateinit var factRowModels: List<FactRowModel>

    @MockK
    private lateinit var getCanadaFacts: GetCanadaFactsInfo

    @Before
    fun setUp() {
        currencyRatesViewModel = CurrencyRatesViewModel(getCanadaFacts)

        currencyRateInfo = CurrencyRateInfo(
            TITLE_LBL,
            listOf(
                Quotes(
                    TITLE_LBL,
                    DESCRIPTION_LBL,
                    HREF_LBL
                )
            ),
            privacy,
            source,
            timestamp,
            quotes
        )

        factRowModels = currencyRateInfo.rows.flatMap {

            listOf(
                FactRowModel(
                    it.title,
                    it.description,
                    it.imageHref
                )
            )
        }
        mutableCanadaLiveData.value = CanadaFactsModel(
            currencyRateInfo.title,
            factRowModels
        )
    }


    /**
     * Whenever there is a change inside API response live data should get updated.
     */
    @Test
    fun `loading facts should update live data`() {

        // Assert
        coEvery { getCanadaFacts.run(any()) } returns Right(currencyRateInfo)

        // Act
//        runBlocking { canadaFactsViewModel.loadCanadaFacts() }

        // Verify
        currencyRatesViewModel.canadaFacts.observeForever {
            it.let {
                it.factRowEntity.size shouldEqualTo 1
                it.factRowEntity[0].description shouldEqualTo DESCRIPTION_LBL
                it.factRowEntity[0].title shouldEqualTo TITLE_LBL
                it.factRowEntity[0].imageHref shouldEqualTo HREF_LBL
            }
        }

    }

    /**
     * Check that the response we got is passed to live data
     */
    @Test
    fun `correct response is passed to handleFactList method`() {

        // Assert


        // Act
        currencyRatesViewModel.handleFactList(currencyRateInfo)

        // Verify
        mutableCanadaLiveData.value shouldEqual CanadaFactsModel(
            currencyRateInfo.title,
            factRowModels
        )

    }
}