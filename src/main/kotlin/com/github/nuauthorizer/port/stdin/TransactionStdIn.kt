package com.github.nuauthorizer.port.stdin

import com.fasterxml.jackson.annotation.JsonRootName
import com.github.nuauthorizer.domain.account.Transaction
import java.time.OffsetDateTime

@JsonRootName("transaction")
data class TransactionStdIn(
    val merchant: String,
    val amount: Long,
    val time: OffsetDateTime,
) : EventIn() {
    fun toDomain() = Transaction(this.merchant, this.amount, this.time)
}