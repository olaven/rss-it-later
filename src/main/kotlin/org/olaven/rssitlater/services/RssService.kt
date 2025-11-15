package org.olaven.rssitlater.services

import org.olaven.rssitlater.database.entities.Article
import org.redundent.kotlin.xml.xml
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class RssService {

    private fun formatRssDate(dateTime: OffsetDateTime): String {
        // RFC 822 format: "Sat, 01 Nov 2025 10:07:21 GMT"
        val formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z")
        return dateTime.format(formatter)
    }

    fun buildFeed(articles: List<Article>, userApiKey: String): String {
        val baseUrl = "https://rss-it-later.fly.dev"
        val feedUrl = "${baseUrl}/feed?api-key=${userApiKey}"
        return xml("rss") {
            attribute("version", "2.0")
            attribute("xmlns:atom", "http://www.w3.org/2005/Atom")
            "channel" {
                "title" { -"RSS It Later" }
                "link" { -baseUrl }
                "description" { -"Your unread articles" }
                "image" {
                    "url" { -"${baseUrl}/logo.svg" }
                    "title" { -"RSS It Later" }
                    "link" { -baseUrl }
                }
                "atom:link" {
                    attribute("href", feedUrl)
                    attribute("rel", "self")
                    attribute("type", "application/rss+xml")
                }

                articles.forEach { article ->
                    "item" {
                        "guid" { -article.url }
                        "title" { -article.title }
                        "link" { -article.url }
                        "description" { -article.description }
                        "pubDate" { -formatRssDate(article.createdAt) }
                    }
                }
            }
        }.toString()
    }
}