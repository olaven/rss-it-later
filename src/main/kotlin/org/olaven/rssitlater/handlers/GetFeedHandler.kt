package org.olaven.rssitlater.handlers

import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.Header
import org.olaven.rssitlater.database.repositories.ArticleRepository
import org.olaven.rssitlater.services.RssService

class GetFeedHandler : Handler {
    // TODO: write automated tests
    override fun handle(ctx: Context) {
        return protectedHandler(ctx) { user ->

            val articleRepository = ArticleRepository()
            val articles = articleRepository.getArticlesByUserId(user.id)

            val rssService = RssService()
            val feed = rssService.buildFeed(articles, user.apiKey)
            ctx.result(feed).header(Header.CONTENT_TYPE, "application/rss+xml")
        }
    }
}
