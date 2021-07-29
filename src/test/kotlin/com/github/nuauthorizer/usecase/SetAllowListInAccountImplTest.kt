package com.github.nuauthorizer.usecase

import com.github.nuauthorizer.domain.account.Account
import com.github.nuauthorizer.domain.account.AccountRepository
import com.github.nuauthorizer.domain.account.AccountViolation
import com.github.nuauthorizer.domain.account.AccountViolation.ACCOUNT_NOT_INITIALIZED
import com.github.nuauthorizer.domain.account.Transaction
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.time.OffsetDateTime
import java.util.*
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
internal class SetAllowListInAccountImplTest {

    @Mock
    private lateinit var accountRepository: AccountRepository

    @InjectMocks
    private lateinit var setAllowListInAccount: SetAllowListInAccountImpl

    @Test
    @DisplayName("When not exist an initialized Account should return ACCOUNT_NOT_INITIALIZED violations")
    fun whenNotExistAnInitializedAccountShouldReturnAccountNotInitializedViolations() {
        whenever(accountRepository.getAccount()).thenReturn(Optional.empty())

        val account = setAllowListInAccount.execute(false)

        assertEquals(account.violations.first(), ACCOUNT_NOT_INITIALIZED)
    }

    @Test
    @DisplayName("When exist an initialized Account should return allow listed attribute equal true")
    fun whenExistAnInitializedAccountShouldReturnAllowListedAttributeEqualTrue() {
        val accountActual = Account(true, 10L)
        val accountUpdated = accountActual.copy(allowListed = true)
        whenever(accountRepository.getAccount()).thenReturn(Optional.of(accountActual))
        whenever(accountRepository.save(accountUpdated)).thenReturn(accountUpdated)

        val account = setAllowListInAccount.execute(true)

        assertTrue(account.allowListed)
    }

}