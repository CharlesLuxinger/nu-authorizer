package com.github.nuauthorizer

import com.github.nuauthorizer.infra.mapper.CustomObjectMapper
import com.github.nuauthorizer.infra.repository.AccountRepositoryImpl
import com.github.nuauthorizer.port.router.EventRouter
import com.github.nuauthorizer.port.stdin.StdIn
import com.github.nuauthorizer.port.stdout.StdOut
import com.github.nuauthorizer.usecase.AddTransactionInAccountImpl
import com.github.nuauthorizer.usecase.CreateAccountImpl
import com.github.nuauthorizer.usecase.SetAllowListInAccountImpl

fun main(args: Array<String>) {
    args.flatMap { line -> StdIn(CustomObjectMapper).execute(line) }
        .map {
            EventRouter(
                CreateAccountImpl(AccountRepositoryImpl()),
                AddTransactionInAccountImpl(AccountRepositoryImpl()),
                SetAllowListInAccountImpl(AccountRepositoryImpl())
            ).execute(it)
        }
        .map { StdOut(CustomObjectMapper).execute(it) }
}