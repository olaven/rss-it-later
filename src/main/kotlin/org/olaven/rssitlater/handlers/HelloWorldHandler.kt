package org.olaven.rssitlater.handlers

import io.javalin.http.Handler

val helloWorldHandler = Handler { ctx -> ctx.result("something") }