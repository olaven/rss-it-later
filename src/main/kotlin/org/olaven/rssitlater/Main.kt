package org.olaven.rssitlater

import io.javalin.Javalin
import org.olaven.rssitlater.database.entities.crudelyMigrate
import org.olaven.rssitlater.handlers.GetFeedHandler
import org.olaven.rssitlater.handlers.PostArticleHandler
import org.olaven.rssitlater.handlers.helloWorldHandler

fun main() {

    crudelyMigrate()

    val app = buildApp()
    app.start(7070)
}

fun buildApp(): Javalin {
    return Javalin.create()
        .get("/", helloWorldHandler)
        .get("/api/get-feed", GetFeedHandler())
        .post("/api/articles", PostArticleHandler())
}

