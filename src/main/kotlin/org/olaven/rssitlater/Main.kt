package org.olaven.rssitlater

import io.javalin.Javalin
import org.olaven.rssitlater.handlers.helloWorldHandler


fun main() {
    val app = Javalin.create(/*config*/)
        .get("/", helloWorldHandler)
        .start(7070)
}