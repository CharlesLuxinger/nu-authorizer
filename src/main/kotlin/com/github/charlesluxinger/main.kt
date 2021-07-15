package com.github.charlesluxinger

import com.github.charlesluxinger.infra.mapper.CustomObjectMapper
import com.github.charlesluxinger.infra.repository.AccountRepositoryImpl
import com.github.charlesluxinger.port.router.EventRouter
import com.github.charlesluxinger.port.stdin.StdIn
import com.github.charlesluxinger.port.stdout.StdOut
import com.github.charlesluxinger.usecase.AddTransactionInAccountImpl
import com.github.charlesluxinger.usecase.CreateAccountImpl

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