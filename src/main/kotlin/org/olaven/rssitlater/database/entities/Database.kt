package org.olaven.rssitlater.database.entities

import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.olaven.rssitlater.environment

// TODO: make this more generic
fun connectDatabase(): Database {
    return Database.connect(
        url = "jdbc:postgresql://${environment["PGHOST"]}:${environment["PGPORT"]}/${environment["PGDATABASE"]}?${environment["PGURLPARAMS"]}",
        user = environment["PGUSER"],
        password = environment["PGPASSWORD"],
        driver = "org.postgresql.Driver",
    )
}

fun crudelyMigrate() {
    // TODO: proper migration setup
    // perhaps write a Gradle plugin for generating
    // scripts based on a file or set of exposed models?
    // Fow now, just crudely create the tables if they don't exist.
    connectDatabase().let {
        transaction {
            tables.forEach { SchemaUtils.create(it) }
        }
    }
}


val tables = arrayOf(ArticlesTable, UsersTable)