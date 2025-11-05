package org.olaven.rssitlater.handlers

import getApiKey
import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.Header
import org.olaven.rssitlater.database.repositories.ArticleRepository
import org.olaven.rssitlater.database.repositories.UserRepository
import org.olaven.rssitlater.services.RssService


class GetFeedHandler : Handler {
    // TODO: move some of this out of the handler
    // TODO: write automated tests
    override fun handle(ctx: Context) {
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

        val articleRepository = ArticleRepository()
        val articles = articleRepository.getArticlesByUserId(user.id)

        val rssService = RssService()
        val feed = rssService.buildFeed(articles)
        ctx.result(feed).header(Header.CONTENT_TYPE, "application/rss+xml")
    }

}
