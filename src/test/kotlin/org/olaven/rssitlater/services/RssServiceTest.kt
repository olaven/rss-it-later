package org.olaven.rssitlater.services

import net.datafaker.Faker
import org.junit.jupiter.api.Test
import org.olaven.rssitlater.database.entities.Article
import java.time.OffsetDateTime
import java.util.*
import kotlin.test.assertNotEquals

class RssServiceTest {

    val service = RssService()
    val faker = Faker()
    val articles = (0..faker.number().numberBetween(1, 100)).map {
        Article(
            id = UUID.randomUUID(),
            title = faker.lorem().word(),
            description = faker.theItCrowd().quotes(),
            createdAt = OffsetDateTime.now(),
            url = faker.internet().url(),
            userId = UUID.randomUUID()
        )
    }

    @Test
    fun `the service returns a string`() {
        val rss= service.buildFeed(articles)
        assertNotEquals(rss.length, 0)
    }
}