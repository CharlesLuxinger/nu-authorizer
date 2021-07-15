package com.github.charlesluxinger.port.stdin

import com.github.charlesluxinger.domain.account.Account
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class AccountStdInTest {

    @Test
    @DisplayName("When convert to domain should return an Account class")
    fun whenConvertToDomainShouldReturnAnAccountClass() {
        val accountStdIn = AccountStdIn(true, 1L)
        val account = accountStdIn.toDomain()

        assertTrue { account is Account }
        assertEquals(account.activeCard, accountStdIn.activeCard)
        assertEquals(account.availableLimit, accountStdIn.availableLimit)
        assertTrue { account.violations.isEmpty() }
        assertTrue { account.transactions.isEmpty() }
    }
}