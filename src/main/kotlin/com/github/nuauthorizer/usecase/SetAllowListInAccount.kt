package com.github.nuauthorizer.usecase

import com.github.nuauthorizer.domain.account.Account

interface SetAllowListInAccount {
    fun execute(active: Boolean): Account
}