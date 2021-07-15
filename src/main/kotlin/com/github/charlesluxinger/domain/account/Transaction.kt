package com.github.charlesluxinger.domain.account

import com.github.charlesluxinger.domain.common.Entity
import java.time.OffsetDateTime

data class Transaction(val merchant: String, val amount: Long, val time: OffsetDateTime) : Entity {

    init {
        if (merchant.isBlank()) throw IllegalArgumentException("#merchant can not be blank")
        if (amount < 0) throw IllegalArgumentException("#amount can not be less than zero")
    }

    fun isDoubleTransaction(newTransaction: Transaction) =
        this.merchant == newTransaction.merchant &&
                this.amount == newTransaction.amount &&
                this.time.isAfter(newTransaction.time.minusMinutes(2))
}