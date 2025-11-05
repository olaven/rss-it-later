package org.olaven.rssitlater.database.repositories

import RepositoryTest
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.UUID
import kotlin.test.assertIsNot


class ArticleRepositoryTest : RepositoryTest() {

    @Test
    fun `getting articles by user returns a list`() {

        val userId = users.random().id
        val result = articleRepository.getArticlesByUserId(userId)

        assertTrue { result.isNotEmpty() }
    }

    @Test
    fun `getting an article works within an existing transaction as well`() {
        val userId = users.random().id
        val result = transaction {
            articleRepository.getArticlesByUserId(userId)
        }

        assertTrue { result.isNotEmpty() }
    }


    @Test
    fun `can get the articles by a user`() {
        val user = users.random()

        val articles = articleRepository.getArticlesByUserId(user.id)
        assertTrue { articles.isNotEmpty() }
        articles.forEach { article ->
            assertEquals(user.id, article.userId)
        }
    }
}