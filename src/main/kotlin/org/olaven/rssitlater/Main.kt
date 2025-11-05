package org.olaven.rssitlater

import io.javalin.Javalin
import org.olaven.rssitlater.database.entities.crudelyMigrate
import org.olaven.rssitlater.handlers.helloWorldHandler

fun main() {

    crudelyMigrate()

    val app = Javalin.create(/*config*/)
        .get("/", helloWorldHandler)
        .start(7070)
}

