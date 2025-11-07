package org.olaven.rssitlater.database.repositories

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.olaven.rssitlater.database.entities.Article
import org.olaven.rssitlater.database.entities.ArticlesTable
import org.olaven.rssitlater.database.entities.toArticle
import java.util.*

class ArticleRepository {
    fun insertArticle(title: String, description: String, url: String, userId: UUID): Article {
        return transactionOrCurrent {
            ArticlesTable.insert {
                it[ArticlesTable.title] = title
                it[ArticlesTable.description] = description
                it[ArticlesTable.url] = url
                it[ArticlesTable.userId] = userId
            }.resultedValues?.map { it.toArticle() }?.firstOrNull()
        }?: throw Error("Failed to insert article")
    }

    fun getArticlesByUserId(userId: UUID): List<Article> {
        return transactionOrCurrent {
            ArticlesTable.selectAll().where(ArticlesTable.userId.eq(userId)).map { row -> row.toArticle() }
        }
    }
}
