package com.github.nuauthorizer.port.stdout

import com.github.nuauthorizer.domain.account.Account
import com.github.nuauthorizer.infra.mapper.CustomObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals


internal class StdOutTest {

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
        StdOut(CustomObjectMapper).execute(Account())

        assertEquals(
            "{\"active-card\":false,\"available-limit\":0,\"allow-listed\":false,\"violations\":[]}",
            outputStreamCaptor.toString().trim()
        )
    }
}