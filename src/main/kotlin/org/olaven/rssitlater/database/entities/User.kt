package org.olaven.rssitlater.database.entities

import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.dao.id.UUIDTable
import org.jetbrains.exposed.v1.datetime.CurrentTimestampWithTimeZone
import org.jetbrains.exposed.v1.datetime.timestampWithTimeZone
import java.time.OffsetDateTime
import java.util.*

object UsersTable : UUIDTable("users") {
    val apiKey = text("api_key").index().uniqueIndex()
    val lastSeen = timestampWithTimeZone("last_seen").defaultExpression(CurrentTimestampWithTimeZone)
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestampWithTimeZone)
}

data class User(
    val id: UUID,
    val apiKey: String,
    val lastSeen: OffsetDateTime,
    val createdAt: OffsetDateTime
)

fun ResultRow.toUser(): User {
    return User(
        id = this[UsersTable.id].value,
        apiKey = this[UsersTable.apiKey],
        lastSeen = this[UsersTable.lastSeen],
        createdAt = this[UsersTable.createdAt],
    )
}