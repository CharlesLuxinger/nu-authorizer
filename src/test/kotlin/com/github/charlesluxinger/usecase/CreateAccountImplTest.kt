package com.github.charlesluxinger.usecase

import com.github.charlesluxinger.domain.account.Account
import com.github.charlesluxinger.domain.account.AccountRepository
import com.github.charlesluxinger.domain.account.AccountViolation.ACCOUNT_ALREADY_INITIALIZED
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
internal class CreateAccountImplTest {

    @Mock
    private lateinit var accountRepository: AccountRepository

    @InjectMocks
    private lateinit var createAccount: CreateAccountImpl

    @Test
    @DisplayName("When is the first save should save successfully")
    fun whenIsTheFirstSaveShouldSaveSuccessfully() {
        val accountActual = Account(true, 10L)

        whenever(accountRepository.getAccount()).thenReturn(Optional.empty())
        whenever(accountRepository.save(accountActual)).thenReturn(accountActual)

        val accountExpected = createAccount.execute(accountActual)

        assertEquals(accountExpected, accountActual)
    }

    @Test
    @DisplayName("When is not the first save should return account with violation")
    fun whenIsNotTheFirstSaveShouldSaveSuccessfully() {
        val accountActual = Account(true, 10L)

        whenever(accountRepository.getAccount()).thenReturn(Optional.of(accountActual))

        val accountExpected = createAccount.execute(accountActual)

        assertTrue { accountExpected.violations.isNotEmpty() }
        assertEquals(accountExpected.violations.first(), ACCOUNT_ALREADY_INITIALIZED)
    }
}