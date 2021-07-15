package com.github.charlesluxinger.infra.repository

import com.github.charlesluxinger.domain.account.Account
import com.github.charlesluxinger.domain.account.AccountRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class AccountRepositoryImplTest {

    private lateinit var repository: AccountRepository

    @BeforeEach
    fun setUp() {
        repository = AccountRepositoryImpl()
        repository.delete()
    }

    @Test
    @DisplayName("When save an account should persistent the data")
    fun whenSaveAnAccountShouldPersistentTheData() {
        val account = Account(true, 1L)

        assertEquals(repository.save(account), account)
    }

    @Test
    @DisplayName("When save twice an account should update the data")
    fun whenSaveTwiceAnAccountShouldUpdateTheData() {
        val account1 = Account(true, 1L)
        val account2 = Account(true, 1L)

        assertEquals(repository.save(account1), account1)
        assertEquals(repository.save(account2), account2)
    }

    @Test
    @DisplayName("When get an account and exist should return the data")
    fun whenGetAnAccountAndExistShouldReturnTheData() {
        val account = Account(true, 1L)
        repository.save(account)

        assertTrue { repository.getAccount().isPresent }
        assertEquals(repository.getAccount().get(), account)
    }

    @Test
    @DisplayName("When get an account and not exist should return empty")
    fun whenGetAnAccountAndNotExistShouldReturnEmpty() {
        assertFalse { repository.getAccount().isPresent }
    }

    @Test
    @DisplayName("When delete an account should return empty")
    fun whenDeleteAnAccountShouldReturnEmpty() {
        val account = Account(true, 1L)
        repository.save(account)

        assertTrue { repository.getAccount().isPresent }

        repository.delete()

        assertFalse { repository.getAccount().isPresent }
    }
}