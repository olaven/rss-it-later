package org.olaven.rssitlater.handlers

import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.HttpStatus
import org.olaven.rssitlater.database.entities.Article
import org.olaven.rssitlater.database.repositories.ArticleRepository


data class ArticleBody(val url: String)

class PostArticleHandler : Handler {
    override fun handle(ctx: Context) {
        return protectedHandler(ctx) { user ->

            val articleData = ctx.bodyValidator(ArticleBody::class.java)
                .check({ it.url.matches(Regex("^https?://.*")) }, "Invalid URL format")
                .getOrThrow { BadRequestResponse() }

            //TODO: Extract title and description
            val articleRepository = ArticleRepository()
            val insertedArticle = articleRepository.insertArticle(
                title = "TEST TITLE",
                description = "TEST DESCRIPTION",
                url = articleData.url,
                userId = user.id
            )

            ctx.status(HttpStatus.CREATED).json(insertedArticle, Article::class.java)
        }
    }

}
