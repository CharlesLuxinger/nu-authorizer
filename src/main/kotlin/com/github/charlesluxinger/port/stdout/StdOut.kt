package com.github.charlesluxinger.port.stdout

import com.github.charlesluxinger.domain.account.Account
import com.github.charlesluxinger.infra.mapper.CustomObjectMapper

class StdOut(private val objectMapper: CustomObjectMapper) {

    fun execute(account: Account) = println(objectMapper.mapper.writeValueAsString(AccountStdOut.of(account)))

}