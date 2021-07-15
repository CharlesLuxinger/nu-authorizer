package com.github.charlesluxinger.usecase

import com.github.charlesluxinger.domain.account.Account
import com.github.charlesluxinger.domain.account.AccountRepository
import com.github.charlesluxinger.domain.account.AccountViolation.ACCOUNT_ALREADY_INITIALIZED

class CreateAccountImpl(private val accountRepository: AccountRepository) : CreateAccount {

    override fun execute(account: Account): Account =
        accountRepository.getAccount()
            .map { account.copy(violations = account.violations + ACCOUNT_ALREADY_INITIALIZED) }
            .orElseGet { accountRepository.save(account) }

}