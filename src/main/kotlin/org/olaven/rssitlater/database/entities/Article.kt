package org.olaven.rssitlater.database.entities

import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.dao.id.UUIDTable
import org.jetbrains.exposed.v1.datetime.CurrentTimestampWithTimeZone
import org.jetbrains.exposed.v1.datetime.timestampWithTimeZone
import java.time.OffsetDateTime
import java.util.UUID

data class Article(
    val id: UUID,
    val title: String,
    val description: String,
    val createdAt: OffsetDateTime,
    val url: String,
    val userId: UUID,
)

object ArticlesTable : UUIDTable("articles") {
    val title = text("title")
    val description = text("description")
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestampWithTimeZone)
    val url = text("url")
    val userId = reference("user_id", UsersTable)
}

fun ResultRow.toArticle(): Article {
    return Article(
        this[ArticlesTable.id].value,
        this[ArticlesTable.title],
        this[ArticlesTable.description],
        this[ArticlesTable.createdAt],
        this[ArticlesTable.url],
        this[ArticlesTable.userId].value,
    )
}
