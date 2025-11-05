package org.olaven.rssitlater

import io.javalin.Javalin
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.exists
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.olaven.rssitlater.database.tables
import org.olaven.rssitlater.handlers.helloWorldHandler

private fun crudelyMigrate() {
    // TODO: proper migration setup
    // perhaps write a Gradle plugin for generating
    // scripts based on a file or set of exposed models?
    // Fow now, just crudely create the tables if they don't exist.
    Database.connect(
        url = "jdbc:postgresql://${environment["PGHOST"]}:${environment["PGPORT"]}/${environment["PGDATABASE"]}",
        driver = "org.postgresql.Driver",
        user = environment["PGUSER"],
        password = environment["PGPASSWORD"]
    )
    transaction {
        tables.forEach { SchemaUtils.create(it) }
    }
}

fun main() {

    crudelyMigrate()

    val app = Javalin.create(/*config*/)
        .get("/", helloWorldHandler)
        .start(7070)
}

