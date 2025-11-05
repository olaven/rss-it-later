package org.olaven.rssitlater.database.repositories

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.olaven.rssitlater.database.entities.User
import org.olaven.rssitlater.database.entities.UsersTable
import org.olaven.rssitlater.database.entities.toUser

class UserRepository {
    fun insertUser(apiKey: String): User? {
        return transactionOrCurrent {
            UsersTable.insert {
                it[UsersTable.apiKey] = apiKey
            }.resultedValues?.map { it.toUser() }?.firstOrNull()
        }
    }

    fun findByApiKey(apiKey: String): User? {
        return transactionOrCurrent {
            UsersTable
                .selectAll()
                .where(UsersTable.apiKey.eq(apiKey))
                .limit(1)
                .map { it.toUser() }
                .firstOrNull()
        }
    }
}