package com.github.nuauthorizer.port.router

import com.github.nuauthorizer.domain.account.Account
import com.github.nuauthorizer.domain.account.Transaction
import com.github.nuauthorizer.domain.common.Entity
import com.github.nuauthorizer.usecase.AddTransactionInAccount
import com.github.nuauthorizer.usecase.CreateAccount

class EventRouter(
    private val createAccount: CreateAccount,
    private val addTransactionInAccount: AddTransactionInAccount
) {

    fun execute(entity: Entity): Account =
        when (entity) {
            is Account -> createAccount.execute(entity)
            is Transaction -> addTransactionInAccount.execute(entity)
            else -> throw NotImplementedError()
        }
}