package com.github.nuauthorizer.port.stdin

import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.github.nuauthorizer.domain.account.Account

@JsonRootName("account")
@JsonNaming(value = PropertyNamingStrategies.KebabCaseStrategy::class)
data class AccountStdIn(
    val activeCard: Boolean,
    val availableLimit: Long,
) : EventIn() {
    fun toDomain() = Account(activeCard = this.activeCard, availableLimit = this.availableLimit)
}