package com.github.nuauthorizer.domain.account

import com.github.nuauthorizer.domain.account.AccountViolation.*
import com.github.nuauthorizer.domain.common.Entity
import java.time.OffsetDateTime

data class Account(
    val activeCard: Boolean = false,
    val availableLimit: Long = 0L,
    val allowListed: Boolean = false,
    val transactions: List<Transaction> = emptyList(),
    val violations: List<AccountViolation> = emptyList()
) : Entity {

    init {
        if (availableLimit < 0) throw IllegalStateException("#availableLimit can not be less than zero")
    }

    companion object {
        fun ofAccountNotInitialized() = Account(violations = listOf(ACCOUNT_NOT_INITIALIZED))
    }

    fun setTransaction(transaction: Transaction) =
        this.isActiveCard()
            .isInsufficientLimit(transaction.amount)
            .isHighFrequencySmallInterval(transaction.time)
            .isDoubleTransaction(transaction)
            .addTransaction(transaction)

    private fun isActiveCard() =
        this.takeIf { it.activeCard } ?: this.copy(violations = this.violations + CARD_NOT_ACTIVE)

    private fun isHighFrequencySmallInterval(time: OffsetDateTime) =
        this.transactions
            .takeUnless { this.allowListed }
            ?.takeLast(3)
            ?.filter { it.time.isAfter(time.minusMinutes(2)) }
            ?.takeIf { it.size > 2 }
            ?.let { this.copy(violations = this.violations + HIGH_FREQUENCY_SMALL_INTERVAL) }
            ?: this

    private fun isDoubleTransaction(transaction: Transaction) =
        this.transactions
            .takeUnless { this.allowListed }
            ?.find { it.isDoubleTransaction(transaction) }
            ?.let { this.copy(violations = this.violations + DOUBLE_TRANSACTION) }
            ?: this

    private fun isInsufficientLimit(value: Long) =
        this.takeIf { this.availableLimit > value }
            ?: this.copy(violations = this.violations + INSUFFICIENT_LIMIT)

    private fun addTransaction(transaction: Transaction) =
        this.takeIf { it.violations.isNotEmpty() }
            ?: this.copy(
                availableLimit = this.availableLimit - transaction.amount,
                transactions = this.transactions + transaction
            )
}