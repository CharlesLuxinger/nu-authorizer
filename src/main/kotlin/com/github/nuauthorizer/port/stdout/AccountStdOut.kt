package com.github.nuauthorizer.port.stdout

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.github.nuauthorizer.domain.account.Account
import com.github.nuauthorizer.domain.account.AccountViolation

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy::class)
data class AccountStdOut(
    val activeCard: Boolean,
    val availableLimit: Long,
    val violations: List<AccountViolation> = emptyList()
) {
    companion object {
        fun of(account: Account) = AccountStdOut(account.activeCard, account.availableLimit, account.violations)
    }
}