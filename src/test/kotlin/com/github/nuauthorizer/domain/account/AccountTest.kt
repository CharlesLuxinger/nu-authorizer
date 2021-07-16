package com.github.nuauthorizer.domain.account

import com.github.nuauthorizer.domain.account.AccountViolation.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime.now
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class AccountTest {

    @Test
    @DisplayName("When call ofAccountNotInitialized method should return an Account with violation ACCOUNT_NOT_INITIALIZED")
    fun whenCallOfAccountNotInitializedMethodShouldReturnAnAccountWithViolationAccountNotInitialized() {
        val account = Account.ofAccountNotInitialized()

        assertEquals(account.violations.first(), ACCOUNT_NOT_INITIALIZED)
    }

    @Test
    @DisplayName("When attribute card active is false should return CARD_NOT_ACTIVE violations")
    fun whenAttributeCardActiveIsFalseShouldReturnCardNotActiveViolations() {
        val accountActual = Account(false)

        val accountExpected = accountActual.setTransaction(Transaction("test", 1L, now()))

        assertEquals(accountExpected.violations.first(), CARD_NOT_ACTIVE)
    }

    @Test
    @DisplayName("When not exist limit should return INSUFFICIENT_LIMIT violations")
    fun whenNotExistLimitShouldReturnInsufficientLimitViolations() {
        val accountActual = Account(true, 0L)

        val accountExpected = accountActual.setTransaction(Transaction("test", 1L, now()))

        assertEquals(accountExpected.violations.first(), INSUFFICIENT_LIMIT)
    }

    @Test
    @DisplayName("When exists 3 transactions in 2 minutes should return HIGH_FREQUENCY_SMALL_INTERVAL violations")
    fun whenExists3TransactionsIn2MinutesReturnHighFrequencySmallIntervalViolations() {
        val transaction1 = Transaction("test1", 1L, now())
        val transaction2 = Transaction("test2", 1L, now().plusSeconds(15))
        val transaction3 = Transaction("test3", 1L, now().plusSeconds(30))
        val accountActual = Account(true, 5L, transactions = listOf(transaction1, transaction2, transaction3))

        val accountExpected = accountActual.setTransaction(Transaction("test4", 1L, now().plusMinutes(1)))

        assertEquals(accountExpected.violations.first(), HIGH_FREQUENCY_SMALL_INTERVAL)
    }

    @Test
    @DisplayName("When exists 2 transactions with same merchant and amount in 2 minutes should return DOUBLE_TRANSACTION violations")
    fun whenExists2TransactionsWithSameMerchantAndAmountIn2MinutesReturnDoubleTransactionViolations() {
        val transaction1 = Transaction("test", 1L, now())
        val transaction2 = Transaction("test", 1L, now().plusSeconds(15))
        val accountActual = Account(true, 5L, transactions = listOf(transaction1, transaction2))

        val accountExpected = accountActual.setTransaction(Transaction("test", 1L, now().plusMinutes(1)))

        assertEquals(accountExpected.violations.first(), DOUBLE_TRANSACTION)
    }

    @Test
    @DisplayName("When exists 2 transactions with same merchant and amount in 2 minutes should return DOUBLE_TRANSACTION violations")
    fun whenNotExistsViolationInATransactionShouldReturnEmptyViolations() {
        val transaction1 = Transaction("test1", 1L, now())
        val transaction2 = Transaction("test2", 1L, now().plusMinutes(10))
        val transaction3 = Transaction("test3", 1L, now().plusMinutes(15))
        val transaction4 = Transaction("test4", 1L, now().plusMinutes(20))
        val accountActual = Account(true, 5L, transactions = listOf(transaction1, transaction2, transaction3))

        val accountExpected = accountActual.setTransaction(transaction4)

        assertTrue { accountExpected.violations.isEmpty() }
    }
}