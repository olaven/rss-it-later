package org.olaven.rssitlater.services

import ArticleExtractorService
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ArticleExtractorServiceTest {

    private val service = ArticleExtractorService()
    private val russelURL = "https://russell-j.com/0583TS.HTM"
    private val knownUrls = listOf(
        russelURL,
        "https://www.pollofpolls.no/?cmd=Kommentarer&do=vis&kommentarid=3893",
        "https://www.nrk.no/norge/svarer-i-nrk-intervju_-ine-eriksen-soreide-vil-bli-hoyre-leder-1.17638997",
    )

    @Test
    fun `the known URLs all return title and description`() {
        knownUrls.forEach { url ->
            val result = runBlocking { service.extract(url) }
            assertThat(result?.description).isNotBlank().isNotNull()
            assertThat(result?.title).isNotBlank().isNotNull()
        }
    }

    @Test
    fun `the known URLs all return unchanged URLs`() {
        knownUrls.forEach { url ->
            val result = runBlocking { service.extract(url) }
            assertThat(result?.url).isEqualTo(url)
        }
    }

    @Test
    fun `the russell URL returns the expected title`() {
        val result = runBlocking { service.extract(russelURL) }
        assertThat(result?.title).isEqualTo("Bertrand Russell：The Triumph of Stupidity")
    }

    @Test
    fun `the russell URL returns the expected description`() {
        val result = runBlocking { service.extract(russelURL) }
        assertThat(result?.description).isEqualTo("One of Bertrand Russell's famous quotes : The fundamental cause of the trouble is that in the modern world the stupid are cocksure while the intelligent are full of doubt")
    }
}