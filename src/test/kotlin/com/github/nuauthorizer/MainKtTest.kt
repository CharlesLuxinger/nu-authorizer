package com.github.nuauthorizer

import com.github.nuauthorizer.domain.account.Account
import com.github.nuauthorizer.infra.mapper.CustomObjectMapper
import com.github.nuauthorizer.port.stdout.StdOut
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

internal class MainKtTest {

    private val standardOut = System.out
    private val outputStreamCaptor = ByteArrayOutputStream()


    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
    }

    @Test
    @DisplayName("When execute StdOut should print account json")
    fun whenExecuteStdOutShouldPrintAccountJson() {
        main(arrayOf("src/test/resources/multiple-violations"))

        assertEquals(
            """
                {"active-card":true,"available-limit":100,"allow-listed":false,"violations":[]}
                {"active-card":true,"available-limit":90,"allow-listed":false,"violations":[]}
                {"active-card":true,"available-limit":70,"allow-listed":false,"violations":[]}
                {"active-card":true,"available-limit":65,"allow-listed":false,"violations":[]}
                {"active-card":true,"available-limit":65,"allow-listed":false,"violations":["high-frequency-small-interval","double-transaction"]}
                {"active-card":true,"available-limit":65,"allow-listed":false,"violations":["insufficient-limit","high-frequency-small-interval"]}
                {"active-card":true,"available-limit":65,"allow-listed":false,"violations":["insufficient-limit","high-frequency-small-interval"]}
                {"active-card":true,"available-limit":50,"allow-listed":false,"violations":[]}
            """.trimIndent(),
            outputStreamCaptor.toString().trim()
        )
    }
}