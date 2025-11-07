package org.olaven.rssitlater.handlers

import ProtectedHandlerTest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.javalin.http.HttpStatus
import io.javalin.testtools.HttpClient
import io.javalin.testtools.JavalinTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.olaven.rssitlater.buildApp
import java.util.*

private val objectMapper = jacksonObjectMapper()


class PostArticleHandlerTest() : ProtectedHandlerTest(
    handler = PostArticleHandler()
) {

    val app = buildApp()


    fun HttpClient.authorizedPostArticle(body: ArticleBody? = null) =
        this.postArticle(testUser.apiKey, body)

    fun HttpClient.postArticle(
        apiKey: String?,
        body: ArticleBody? = null
    ) = this.request("/feed/articles${apiKey?.let { "?api-key=$apiKey" } ?: ""}") { builder ->

        val jsonBody = body?.let { objectMapper.writeValueAsString(it) } ?: ""
        val requestBody = jsonBody.toRequestBody("application/json".toMediaType())

        builder.method("POST", requestBody)
    }


    @Test
    fun `returns bad request if auth header is not defined`() = JavalinTest.test(app) { server, client ->
        val response = client.postArticle(null)
        assertThat(response.code).isEqualTo(HttpStatus.BAD_REQUEST.code)
    }

    @Test
    fun `returns 401 if token is wrong`() = JavalinTest.test(app) { server, client ->
        val response = client.postArticle(UUID.randomUUID().toString())
        assertThat(response.code).isEqualTo(HttpStatus.UNAUTHORIZED.code)
    }

    @Test
    fun `returns 400 if auth is good, but no article is present`() = JavalinTest.test(app) { server, client ->
        val response = client.authorizedPostArticle()
        assertThat(response.code).isEqualTo(HttpStatus.BAD_REQUEST.code)
    }

    @Test
    fun `returns 201 if article is created`() = JavalinTest.test(app) { server, client ->
        val response = client.authorizedPostArticle(
            body = ArticleBody(
                url = "https://example.com"
            )
        )
        assertThat(response.code).isEqualTo(HttpStatus.CREATED.code)
    }

    @Test
    fun `returns JSON if article is created`() = JavalinTest.test(app) { server, client ->
        val articleBody = ArticleBody(
            url = "https://example.com"
        )
        val response = client.authorizedPostArticle(
            body = articleBody
        )

        val jsonNode = objectMapper.readTree(response.body!!.string())

        assertThat(jsonNode["url"].asText()).isEqualTo(articleBody.url)
    }
}