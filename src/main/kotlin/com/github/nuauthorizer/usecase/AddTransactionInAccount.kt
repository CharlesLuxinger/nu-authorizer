package com.github.nuauthorizer.usecase

import com.github.nuauthorizer.domain.account.Account
import com.github.nuauthorizer.domain.account.Transaction

interface AddTransactionInAccount {

    fun execute(transaction: Transaction): Account
}