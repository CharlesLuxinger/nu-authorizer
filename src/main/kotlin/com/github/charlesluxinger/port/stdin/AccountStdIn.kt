package com.github.charlesluxinger.port.stdin

import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.github.charlesluxinger.domain.account.Account

@JsonRootName("account")
@JsonNaming(value = PropertyNamingStrategies.KebabCaseStrategy::class)
data class AccountStdIn(
    val activeCard: Boolean,
    val availableLimit: Long,
) : EventIn() {
    override fun toDomain() = Account(this.activeCard, this.availableLimit)
}