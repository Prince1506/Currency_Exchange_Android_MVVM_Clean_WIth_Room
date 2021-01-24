package com.mvvm_clean.currency_exchange.features.login

import com.mvvm_clean.currency_exchange.UnitTest
import com.mvvm_clean.currency_exchange.features.login.domain.Authenticator
import org.amshove.kluent.shouldBe
import org.junit.Test

class AuthenticatorTest : UnitTest() {

    private val authenticator = Authenticator()

    @Test
    fun `returns default value`() {
        authenticator.userLoggedIn() shouldBe true
    }
}
