package com.github.charlesluxinger.infra.repository


import com.github.charlesluxinger.domain.account.Account
import com.github.charlesluxinger.domain.account.AccountRepository
import java.util.*
import java.util.Optional.empty
import java.util.Optional.of

class AccountRepositoryImpl : AccountRepository {

    companion object {
        private var database: Optional<Account> = empty()
    }

    override fun save(account: Account) = account.also { database = of(it) }

    override fun getAccount() = database

    override fun delete() {
        database = empty()
    }
}
