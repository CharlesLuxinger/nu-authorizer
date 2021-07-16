package com.github.nuauthorizer.port.stdin

import com.github.nuauthorizer.infra.mapper.CustomObjectMapper
import java.io.File

class StdIn(
    private val objectMapper: CustomObjectMapper
) {
    fun execute(filePath: String): List<EventIn> =
        File(filePath)
            .readLines(Charsets.UTF_8)
            .map { objectMapper.mapper.readerFor(EventIn::class.java).readValue(it) }

}