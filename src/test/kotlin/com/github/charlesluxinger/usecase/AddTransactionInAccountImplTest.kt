package com.github.charlesluxinger.usecase

import com.github.charlesluxinger.domain.account.Account
import com.github.charlesluxinger.domain.account.AccountRepository
import com.github.charlesluxinger.domain.account.AccountViolation.*
import com.github.charlesluxinger.domain.account.Transaction
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.time.OffsetDateTime.now
import java.util.Optional.empty
import java.util.Optional.of
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
internal class AddTransactionInAccountImplTest {

    @Mock
    private lateinit var accountRepository: AccountRepository

    @InjectMocks
    private lateinit var addTransactionInAccount: AddTransactionInAccountImpl


    @Test
    @DisplayName("When not exist an initialized Account should return ACCOUNT_NOT_INITIALIZED violations")
    fun whenNotExistAnInitializedAccountShouldReturnAccountNotInitializedViolations() {
        whenever(accountRepository.getAccount()).thenReturn(empty())

        val account = addTransactionInAccount.execute(Transaction("test", 1L, now()))

        assertEquals(account.violations.first(), ACCOUNT_NOT_INITIALIZED)
    }

    @Test
    @DisplayName("When attribute card active is false should return CARD_NOT_ACTIVE violations")
    fun whenAttributeCardActiveIsFalseShouldReturnCardNotActiveViolations() {
        whenever(accountRepository.getAccount()).thenReturn(of(Account(false)))

        val account = addTransactionInAccount.execute(Transaction("test", 1L, now()))

        assertEquals(account.violations.first(), CARD_NOT_ACTIVE)
    }

    @Test
    @DisplayName("When not exist limit should return INSUFFICIENT_LIMIT violations")
    fun whenNotExistLimitShouldReturnInsufficientLimitViolations() {
        whenever(accountRepository.getAccount()).thenReturn(of(Account(true, 0L)))

        val account = addTransactionInAccount.execute(Transaction("test", 1L, now()))

        assertEquals(account.violations.first(), INSUFFICIENT_LIMIT)
    }

    @Test
    @DisplayName("When exists 3 transactions in 2 minutes should return HIGH_FREQUENCY_SMALL_INTERVAL violations")
    fun whenExists3TransactionsIn2MinutesReturnHighFrequencySmallIntervalViolations() {
        val transaction1 = Transaction("test1", 1L, now())
        val transaction2 = Transaction("test2", 1L, now().plusSeconds(15))
        val transaction3 = Transaction("test3", 1L, now().plusSeconds(30))

        whenever(accountRepository.getAccount())
            .thenReturn(of(Account(true, 5L, transactions = listOf(transaction1, transaction2, transaction3))))

        val account = addTransactionInAccount.execute(Transaction("test4", 1L, now().plusMinutes(1)))

        assertEquals(account.violations.first(), HIGH_FREQUENCY_SMALL_INTERVAL)
    }

    @Test
    @DisplayName("When exists 2 transactions with same merchant and amount in 2 minutes should return DOUBLE_TRANSACTION violations")
    fun whenExists2TransactionsWithSameMerchantAndAmountIn2MinutesReturnDoubleTransactionViolations() {
        val transaction1 = Transaction("test", 1L, now())
        val transaction2 = Transaction("test", 1L, now().plusSeconds(15))

        whenever(accountRepository.getAccount())
            .thenReturn(of(Account(true, 5L, transactions = listOf(transaction1, transaction2))))

        val account = addTransactionInAccount.execute(Transaction("test", 1L, now().plusMinutes(1)))

        assertEquals(account.violations.first(), DOUBLE_TRANSACTION)
    }

    @Test
    @DisplayName("When exists 2 transactions with same merchant and amount in 2 minutes should return DOUBLE_TRANSACTION violations")
    fun whenNotExistsViolationInATransactionShouldReturnEmptyViolations() {
        val transaction1 = Transaction("test1", 1L, now())
        val transaction2 = Transaction("test2", 1L, now().plusMinutes(10))
        val transaction3 = Transaction("test3", 1L, now().plusMinutes(15))
        val transaction4 = Transaction("test4", 1L, now().plusMinutes(20))
        val accountSaved = Account(true, 5L, transactions = listOf(transaction1, transaction2, transaction3))
        val accountUpdated = accountSaved.copy(transactions = accountSaved.transactions + transaction4)

        whenever(accountRepository.getAccount()).thenReturn(of(accountSaved))
        whenever(accountRepository.save(any())).thenReturn(accountUpdated)

        val account = addTransactionInAccount.execute(transaction4)

        assertTrue { account.violations.isEmpty() }
    }
}