package com.github.nuauthorizer

import com.github.nuauthorizer.infra.mapper.CustomObjectMapper
import com.github.nuauthorizer.infra.repository.AccountRepositoryImpl
import com.github.nuauthorizer.port.router.EventRouter
import com.github.nuauthorizer.port.stdin.StdIn
import com.github.nuauthorizer.port.stdout.StdOut
import com.github.nuauthorizer.usecase.AddTransactionInAccountImpl
import com.github.nuauthorizer.usecase.CreateAccountImpl

fun main(args: Array<String>) {
    args.flatMap { line -> StdIn(CustomObjectMapper).execute(line).map { it.toDomain() } }
        .map {
            EventRouter(
                CreateAccountImpl(AccountRepositoryImpl()),
                AddTransactionInAccountImpl(AccountRepositoryImpl())
            ).execute(it)
        }
        .map { StdOut(CustomObjectMapper).execute(it) }
}