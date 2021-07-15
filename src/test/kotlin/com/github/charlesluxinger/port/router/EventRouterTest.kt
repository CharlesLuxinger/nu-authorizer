package com.github.charlesluxinger.port.router

import com.github.charlesluxinger.domain.account.Account
import com.github.charlesluxinger.domain.account.Transaction
import com.github.charlesluxinger.domain.common.Entity
import com.github.charlesluxinger.usecase.AddTransactionInAccount
import com.github.charlesluxinger.usecase.CreateAccount
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

    @InjectMocks
    private lateinit var eventRouter: EventRouter

    @Test
    @DisplayName("When is an Account Event should execute Create Account")
    fun whenIsAnAccountShouldExecuteCreateAccount() {
        val accountActual = Account()

        whenever(createAccount.execute(accountActual)).thenReturn(accountActual)

        val accountExpected = eventRouter.execute(accountActual)

        assertEquals(accountExpected, accountActual)
    }

    @Test
    @DisplayName("When is a Transaction Event should execute Add Transaction In Account")
    fun whenIsATransactionShouldExecuteAddTransactionInAccount() {
        val transactionActual = Transaction("test", 1L, now())
        val accountActual = Account()

        whenever(addTransactionInAccount.execute(transactionActual)).thenReturn(accountActual)

        val accountExpected = eventRouter.execute(transactionActual)

        assertEquals(accountExpected, accountActual)
    }

    @Test
    @DisplayName("When is a Not Implement Class should throw exception")
    fun whenIsANotNotImplementClassShouldThrowException() {
        assertThrows<NotImplementedError> {
            eventRouter.execute(NotImplementClass())
        }
    }

    private class NotImplementClass : Entity
}