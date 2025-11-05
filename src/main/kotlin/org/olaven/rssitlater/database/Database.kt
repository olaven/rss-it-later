package org.olaven.rssitlater.database

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.migration.jdbc.MigrationUtils

object Article: Table("articles") {
    val id = uuid("id")
}


val tables = arrayOf(Article)