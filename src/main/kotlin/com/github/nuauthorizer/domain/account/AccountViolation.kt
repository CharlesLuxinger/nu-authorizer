package com.github.nuauthorizer.domain.account

import com.fasterxml.jackson.annotation.JsonValue

enum class AccountViolation(@JsonValue private val message: String) {

    ACCOUNT_ALREADY_INITIALIZED("account-already-initialized"),
    ACCOUNT_NOT_INITIALIZED("account-not-initialized"),
    CARD_NOT_ACTIVE("card-not-active"),
    INSUFFICIENT_LIMIT("insufficient-limit"),
    HIGH_FREQUENCY_SMALL_INTERVAL("high-frequency-small-interval"),
    DOUBLE_TRANSACTION("double-transaction")

}