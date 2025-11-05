package org.olaven.rssitlater.handlers

import io.github.cdimascio.dotenv.dotenv
import io.javalin.http.Handler
import org.olaven.rssitlater.environment


val helloWorldHandler = Handler { ctx -> ctx.result("Hello World!") }