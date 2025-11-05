package org.olaven.rssitlater.database

import org.jetbrains.exposed.v1.core.dao.id.UUIDTable
import org.jetbrains.exposed.v1.datetime.timestampWithTimeZone

object User : UUIDTable("user") {
    val apiKey = text("api_key").index()
    val lastSeen = timestampWithTimeZone("last_seen")
    val createdAt = timestampWithTimeZone("created_at")
}

object Article : UUIDTable("articles") {
    val title = text("title")
    val description = text("description")
    val createdAt = timestampWithTimeZone("created_at")
    val url = text("url")
    val user = reference("user_id", User)
}


val tables = arrayOf(Article)