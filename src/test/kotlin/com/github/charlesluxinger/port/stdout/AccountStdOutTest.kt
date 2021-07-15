package com.github.charlesluxinger.port.stdout

import com.github.charlesluxinger.domain.account.Account
import com.github.charlesluxinger.domain.account.AccountViolation.INSUFFICIENT_LIMIT
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class AccountStdOutTest {

    @Test
    @DisplayName("When convert of domain should return an AccountStdOut class")
    fun whenConvertOfDomainShouldReturnAnAccountStdOutClass() {
        val account = Account(true, 1L, violations = listOf(INSUFFICIENT_LIMIT))
        val accountStdOut = AccountStdOut.of(account)

        assertEquals(accountStdOut.violations, account.violations)
        assertEquals(accountStdOut.activeCard, account.activeCard)
        assertEquals(accountStdOut.availableLimit, account.availableLimit)
    }
}