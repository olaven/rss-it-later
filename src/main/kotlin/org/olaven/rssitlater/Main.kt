package org.olaven.rssitlater

import io.javalin.Javalin
import org.olaven.rssitlater.database.entities.crudelyMigrate
import org.olaven.rssitlater.handlers.GetFeedHandler
import org.olaven.rssitlater.handlers.PostArticleHandler
import org.olaven.rssitlater.handlers.WebsiteHandler

fun main() {

    crudelyMigrate()
    buildApp().start("0.0.0.0",environment["PORT"].toInt())
}

fun buildApp(): Javalin {
    return Javalin.create { config ->
        config.staticFiles.add("/public")
    }
        .get("/", WebsiteHandler())
        .get("/feed", GetFeedHandler())
        .post("/feed/articles", PostArticleHandler())
}

