package com.github.nuauthorizer.port.router

import com.github.nuauthorizer.domain.account.Transaction
import com.github.nuauthorizer.domain.common.Entity
import com.github.nuauthorizer.port.stdin.AccountStdIn
import com.github.nuauthorizer.port.stdin.AllowListStdIn
import com.github.nuauthorizer.port.stdin.EventIn
import com.github.nuauthorizer.port.stdin.TransactionStdIn
import com.github.nuauthorizer.usecase.AddTransactionInAccount
import com.github.nuauthorizer.usecase.CreateAccount
import com.github.nuauthorizer.usecase.SetAllowListInAccount
import com.github.nuauthorizer.usecase.SetAllowListInAccountImpl
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.time.OffsetDateTime.now
import kotlin.test.assertEquals


@ExtendWith(MockitoExtension::class)
internal class EventRouterTest {

    @Mock
    private lateinit var createAccount: CreateAccount

    @Mock
    private lateinit var addTransactionInAccount: AddTransactionInAccount

    @Mock
    private lateinit var setAllowListInAccount: SetAllowListInAccount

    @InjectMocks
    private lateinit var eventRouter: EventRouter

    private val accountStdIn = AccountStdIn(activeCard = false, 0L)
    private val accountActual = accountStdIn.toDomain()

    private val transactionStdIn = TransactionStdIn("test", 1L, now())
    private val transactionActual = transactionStdIn.toDomain()

    @Test
    @DisplayName("When is an Account Event should execute Create Account")
    fun whenIsAnAccountShouldExecuteCreateAccount() {
        whenever(createAccount.execute(accountActual)).thenReturn(accountActual)

        val accountExpected = eventRouter.execute(accountStdIn)

        assertEquals(accountExpected, accountActual)
    }

    @Test
    @DisplayName("When is a Transaction Event should execute Add Transaction In Account")
    fun whenIsATransactionShouldExecuteAddTransactionInAccount() {
        whenever(addTransactionInAccount.execute(transactionActual)).thenReturn(accountActual)

        val accountExpected = eventRouter.execute(transactionStdIn)

        assertEquals(accountExpected, accountActual)
    }

    @Test
    @DisplayName("When is an Allow List Event should execute Set Allow List In Account")
    fun whenIsAnAllowListEventShouldExecuteSetAllowListInAccount() {
        val active = true
        val accountUpdated = accountActual.copy(allowListed = active)
        whenever(setAllowListInAccount.execute(active)).thenReturn(accountUpdated)

        val accountExpected = eventRouter.execute(AllowListStdIn(active))

        assertEquals(accountExpected, accountUpdated)
    }

    @Test
    @DisplayName("When is a Not Implement Class should throw exception")
    fun whenIsANotNotImplementClassShouldThrowException() {
        assertThrows<NotImplementedError> {
            eventRouter.execute(NotImplementClass())
        }
    }

    private class NotImplementClass : EventIn()
}