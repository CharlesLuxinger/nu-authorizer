package com.github.charlesluxinger.domain.account

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.OffsetDateTime.now
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class TransactionTest {

    @Test
    @DisplayName("When attribute merchant is blank should throw exception")
    fun whenAttributeMerchantIsBlankShouldThrowException() {
        assertThrows<IllegalArgumentException> { Transaction("", 1L, now()) }
    }

    @Test
    @DisplayName("When attribute amount is less than zero should throw exception")
    fun whenAttributeAmountIsLessThanZeroShouldThrowException() {
        assertThrows<IllegalArgumentException> { Transaction("", -1L, now()) }
    }

    @Test
    @DisplayName("When attributes is valid should not throw exception")
    fun whenAttributesIsValidShouldNotThrowException() {
        assertDoesNotThrow { Transaction("not blank", 1L, now()) }
    }

    @Test
    @DisplayName("When amount and merchant are equals and time is 2 minutes after should return false")
    fun whenAmountAndMerchantAreEqualsAndTimeIs2MinutesShouldReturnFalse() {
        val transaction1 = Transaction("not blank", 1L, now())
        val transaction2 = Transaction("not blank", 1L, now().plusMinutes(3))

        assertFalse { transaction1.isDoubleTransaction(transaction2) }
    }

    @Test
    @DisplayName("When amount and merchant are equals and time is 2 minutes after should return False")
    fun whenAmountAndMerchantAreEqualsAndTimeIs2MinutesShouldReturnTrue() {
        val transaction1 = Transaction("not blank", 1L, now())
        val transaction2 = Transaction("not blank", 1L, now().plusMinutes(2))

        assertFalse { transaction1.isDoubleTransaction(transaction2) }
    }

    @Test
    @DisplayName("When amount and merchant are equals and time is 2 minutes before should return true")
    fun whenAmountAndMerchantAreEqualsAndTimeIs2MinutesBeforeShouldReturnTrue() {
        val transaction1 = Transaction("not blank", 1L, now())
        val transaction2 = Transaction("not blank", 1L, now().plusMinutes(1))

        assertTrue { transaction1.isDoubleTransaction(transaction2) }
    }
}