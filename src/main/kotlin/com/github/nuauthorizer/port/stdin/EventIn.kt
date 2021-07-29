package com.github.nuauthorizer.port.stdin

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes(
    JsonSubTypes.Type(value = AccountStdIn::class, name = "account"),
    JsonSubTypes.Type(value = TransactionStdIn::class, name = "transaction"),
    JsonSubTypes.Type(value = AllowListStdIn::class, name = "allow-list")
)
abstract class EventIn