package org.olaven.rssitlater.handlers

import io.javalin.http.Context
import jakarta.servlet.http.HttpServletRequest
import org.olaven.rssitlater.database.entities.User
import org.olaven.rssitlater.database.repositories.UserRepository

fun getApiKey(request: HttpServletRequest): String? {

    return request.getParameter("api-key")
}

fun protectedHandler(ctx: Context, fn: (user: User) -> Unit) {
    val userRepository = UserRepository()
    val apiKey = getApiKey(ctx.req())
    if (apiKey == null) {
        ctx.status(400).json("API Key Missing")
        return;
    }

    val user = userRepository.findByApiKey(apiKey)
    if (user == null) {
        ctx.status(401).json("Invalid API Key");
        return;
    }

    return fn(user)
}