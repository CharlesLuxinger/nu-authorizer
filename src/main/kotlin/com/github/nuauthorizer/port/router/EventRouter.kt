package com.github.nuauthorizer.port.router

import com.github.nuauthorizer.domain.account.Account
import com.github.nuauthorizer.port.stdin.AccountStdIn
import com.github.nuauthorizer.port.stdin.AllowListStdIn
import com.github.nuauthorizer.port.stdin.EventIn
import com.github.nuauthorizer.port.stdin.TransactionStdIn
import com.github.nuauthorizer.usecase.AddTransactionInAccount
import com.github.nuauthorizer.usecase.CreateAccount
import com.github.nuauthorizer.usecase.SetAllowListInAccount

class EventRouter(
    private val createAccount: CreateAccount,
    private val addTransactionInAccount: AddTransactionInAccount,
    private val setAllowListInAccount: SetAllowListInAccount
) {

    fun execute(entity: EventIn): Account =
        when (entity) {
            is AccountStdIn -> createAccount.execute(entity.toDomain())
            is TransactionStdIn -> addTransactionInAccount.execute(entity.toDomain())
            is AllowListStdIn -> setAllowListInAccount.execute(entity.active)
            else -> throw NotImplementedError()
        }
}