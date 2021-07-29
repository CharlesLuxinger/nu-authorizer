package com.github.nuauthorizer.usecase

import com.github.nuauthorizer.domain.account.Account
import com.github.nuauthorizer.domain.account.AccountRepository

class SetAllowListInAccountImpl(private val accountRepository: AccountRepository) : SetAllowListInAccount {

    override fun execute(active: Boolean): Account =
        accountRepository.getAccount()
            .map { accountRepository.save(it.copy(allowListed = active)) }
            .orElse(Account.ofAccountNotInitialized())
}
