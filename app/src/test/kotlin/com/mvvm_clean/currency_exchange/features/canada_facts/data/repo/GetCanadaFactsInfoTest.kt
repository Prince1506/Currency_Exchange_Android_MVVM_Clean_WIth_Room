package com.mvvm_clean.currency_exchange.features.canada_facts.data.repo

import com.mvvm_clean.currency_exchange.UnitTest
import com.mvvm_clean.currency_exchange.core.domain.functional.Either.Right
import com.mvvm_clean.currency_exchange.core.domain.interactor.UseCase
import com.mvvm_clean.currency_exchange.features.canada_facts.domain.repo.AboutCanadaRepository
import com.mvvm_clean.currency_exchange.features.canada_facts.domain.use_cases.GetCanadaFactsInfo
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCanadaFactsInfoTest : UnitTest() {

    private lateinit var getCanadaFactsInfo: GetCanadaFactsInfo
    private lateinit var canadaFactList: CanadaFactsInfo
    private val TITLE_LBL = "title"
    private val DESCRIPTION_LBL = "description"
    private val HREF_LBL = "href"
    @MockK
    private lateinit var aboutCanadaRepository: AboutCanadaRepository

    @Before
    fun setUp() {

        getCanadaFactsInfo = GetCanadaFactsInfo(aboutCanadaRepository)
        canadaFactList = CanadaFactsInfo(
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
        every { aboutCanadaRepository.getFacts() } returns Right(CanadaFactsInfo.empty)
    }

    /**
     * Check that API method is called once to get fact list
     */
    @Test
    fun `should get data from repository`() {

        // Act
        runBlocking { getCanadaFactsInfo.run(UseCase.None()) }

        // Verify
        verify(exactly = 1) { aboutCanadaRepository.getFacts() }
    }
}
