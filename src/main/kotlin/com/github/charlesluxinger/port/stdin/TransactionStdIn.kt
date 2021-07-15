package com.github.charlesluxinger.port.stdin

import com.fasterxml.jackson.annotation.JsonRootName
import com.github.charlesluxinger.domain.account.Transaction
import java.time.OffsetDateTime

@JsonRootName("transaction")
data class TransactionStdIn(
    val merchant: String,
    val amount: Long,
    val time: OffsetDateTime,
) : EventIn() {
    override fun toDomain() = Transaction(this.merchant, this.amount, this.time)
}