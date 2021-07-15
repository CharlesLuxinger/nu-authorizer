package com.github.charlesluxinger.usecase

import com.github.charlesluxinger.domain.account.Account
import com.github.charlesluxinger.domain.account.AccountRepository
import com.github.charlesluxinger.domain.account.Transaction

class AddTransactionInAccountImpl(private val accountRepository: AccountRepository) : AddTransactionInAccount {

    override fun execute(transaction: Transaction): Account =
        accountRepository.getAccount()
            .map { it.setTransaction(transaction) }
            .map { if (it.violations.isEmpty()) accountRepository.save(it) else it }
            .orElse(Account.ofAccountNotInitialized())
}