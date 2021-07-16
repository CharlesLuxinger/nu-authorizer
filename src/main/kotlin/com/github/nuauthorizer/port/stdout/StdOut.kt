package com.github.nuauthorizer.port.stdout

import com.github.nuauthorizer.domain.account.Account
import com.github.nuauthorizer.infra.mapper.CustomObjectMapper

class StdOut(private val objectMapper: CustomObjectMapper) {

    fun execute(account: Account) = println(objectMapper.mapper.writeValueAsString(AccountStdOut.of(account)))

}