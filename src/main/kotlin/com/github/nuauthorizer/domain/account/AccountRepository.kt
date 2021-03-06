package com.github.nuauthorizer.domain.account

import java.util.*

interface AccountRepository {

    fun save(account: Account): Account
    fun getAccount(): Optional<Account>
    fun delete()

}