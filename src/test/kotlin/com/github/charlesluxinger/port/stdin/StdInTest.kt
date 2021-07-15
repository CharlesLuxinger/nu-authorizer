package com.github.charlesluxinger.port.stdin

import com.github.charlesluxinger.infra.mapper.CustomObjectMapper
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.FileNotFoundException

internal class StdInTest {

    private var stdIn: StdIn = StdIn(CustomObjectMapper)

    @Test
    @DisplayName("With a Valid Path File Should Return AccountStdIn Instance")
    fun withAValidPathFileShouldReturnAccountInstance() {
        stdIn.execute("src/test/resources/account")
            .first()
            .let { assertTrue(it is AccountStdIn) }
    }

    @Test
    @DisplayName("With a Valid Path File Should Return TransactionStdIn Instance")
    fun withAValidPathFileShouldReturnTransactionInstance() {
        stdIn.execute("src/test/resources/transaction")
            .first()
            .let { assertTrue(it is TransactionStdIn) }
    }

    @Test
    @DisplayName("With an Invalid Valid Path File Should Throw Exception")
    fun withAnInvalidValidPathFileShouldThrowException() {
        assertThrows(FileNotFoundException::class.java) { stdIn.execute("wrongPath") }
    }
}