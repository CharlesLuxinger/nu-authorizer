package com.github.nuauthorizer.port.stdin

import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("allow-list")
data class AllowListStdIn(
    val active: Boolean
) : EventIn()