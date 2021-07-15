package com.github.charlesluxinger.usecase

import com.github.charlesluxinger.domain.account.Account
import com.github.charlesluxinger.domain.account.Transaction

interface AddTransactionInAccount {

    fun execute(transaction: Transaction): Account
}