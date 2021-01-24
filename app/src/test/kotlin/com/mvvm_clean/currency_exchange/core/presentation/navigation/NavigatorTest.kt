package com.mvvm_clean.currency_exchange.core.presentation.navigation

import com.mvvm_clean.currency_exchange.AndroidTest
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.ui.activities.CanadaFactListActivity
import com.mvvm_clean.currency_exchange.features.login.domain.Authenticator
import com.mvvm_clean.currency_exchange.shouldNavigateTo
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class NavigatorTest : AndroidTest() {

    private lateinit var navigator: Navigator

    @MockK
    private lateinit var authenticator: Authenticator

    @Before
    fun setup() {
        navigator = Navigator(authenticator)
    }

    @Test
    fun `should forward user to canada Fact screen`() {
        every { authenticator.userLoggedIn() } returns true

        navigator.showScreens(context())
        verify(exactly = 1) { authenticator.userLoggedIn() }
        CurrencyExchangeNavigatorActivity::class shouldNavigateTo CanadaFactListActivity::class
    }
}