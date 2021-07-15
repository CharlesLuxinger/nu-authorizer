package com.github.charlesluxinger.usecase

import com.github.charlesluxinger.domain.account.Account

interface CreateAccount {

    fun execute(account: Account): Account
}