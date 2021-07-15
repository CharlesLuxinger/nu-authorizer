package com.github.charlesluxinger.port.stdin

import com.github.charlesluxinger.domain.account.Transaction
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime.now
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class TransactionStdInTest {

    @Test
    @DisplayName("When convert to domain should return an Account class")
    fun whenConvertToDomainShouldReturnAnAccountClass() {
        val transactionStdIn = TransactionStdIn("test", 1L, now())
        val transaction = transactionStdIn.toDomain()

        assertTrue { transaction is Transaction }
        assertEquals(transaction.amount, transactionStdIn.amount)
        assertEquals(transaction.merchant, transactionStdIn.merchant)
        assertEquals(transaction.time, transactionStdIn.time)
    }
}