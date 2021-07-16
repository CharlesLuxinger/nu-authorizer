package com.github.nuauthorizer.usecase

import com.github.nuauthorizer.domain.account.Account

interface CreateAccount {

    fun execute(account: Account): Account
}