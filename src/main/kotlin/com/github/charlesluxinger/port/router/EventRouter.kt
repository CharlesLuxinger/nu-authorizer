package com.github.charlesluxinger.port.router

import com.github.charlesluxinger.domain.account.Account
import com.github.charlesluxinger.domain.account.Transaction
import com.github.charlesluxinger.domain.common.Entity
import com.github.charlesluxinger.usecase.AddTransactionInAccount
import com.github.charlesluxinger.usecase.CreateAccount

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