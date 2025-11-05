package org.olaven.rssitlater.database.repositories

import org.jetbrains.exposed.v1.jdbc.transactions.TransactionManager
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

/**
 * Executes the given function within
 * an existing transaction if it exists or
 * a new transaction if non exists.
 */
fun <T> transactionOrCurrent(fn: () -> T): T {
    val currentTransaction = TransactionManager.currentOrNull()
    return if (currentTransaction != null) {
        fn()
    } else {
        transaction {
            fn()
        }
    }
}