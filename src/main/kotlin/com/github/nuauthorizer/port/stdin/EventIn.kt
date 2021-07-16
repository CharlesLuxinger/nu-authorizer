package com.github.nuauthorizer.port.stdin

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.github.nuauthorizer.domain.common.Entity

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes(
    JsonSubTypes.Type(value = AccountStdIn::class, name = "account"),
    JsonSubTypes.Type(value = TransactionStdIn::class, name = "transaction")
)
abstract class EventIn {

    abstract fun toDomain(): Entity
}