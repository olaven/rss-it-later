package org.olaven.rssitlater.handlers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.javalin.http.Context
import io.javalin.http.Handler
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.html.*
import kotlinx.html.stream.appendHTML

class WebsiteHandler : Handler {

    private val styles = CssBuilder().apply {
        rule(":root") {
            put("--bg", "#f5f3ed")
            put("--paper", "#fdfcf8")
            put("--text", "#2d2d2d")
            put("--border", "#e8e6dd")
        }

        rule("body") {
            backgroundColor = Color("var(--bg)")
            color = Color("var(--text)")
            fontFamily = "'Courier New', monospace"
            fontSize = 16.px
            lineHeight = LineHeight("1.6")
            padding = Padding(2.rem)
        }

        rule("main") {
            maxWidth = 720.px
            margin = Margin(0.px, LinearDimension.auto)
            backgroundColor = Color("var(--paper)")
            padding = Padding(3.rem, 2.5.rem)
            border = Border(1.px, BorderStyle.solid, Color("var(--border)"))
        }

        rule("h1") {
            fontWeight = FontWeight.normal
            borderBottom = Border(2.px, BorderStyle.solid, Color("var(--text)"))
            paddingBottom = 0.5.rem
        }
    }

    override fun handle(ctx: Context) {
        val html = buildString {
            appendHTML().html {
                head {
                    title { +"RSS It Later" }
                    meta(charset = "UTF-8")
                    meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
                    // "Fork me on Github": https://simonwhitaker.github.io/github-fork-ribbon-css/
                    link {
                        rel = "stylesheet"
                        href =
                            "https://cdnjs.cloudflare.com/ajax/libs/github-fork-ribbon-css/0.2.3/gh-fork-ribbon.min.css"
                    }
                    style {
                        unsafe { raw(styles.toString()) }
                    }
                }
                body {
                    main {
                        h1 { +"RSS It Later" }
                        p { +"Save articles to your own personal RSS feed." }
                        p {
                            +"RSS It Later is a small web service that allows you to save articles to your own RSS feed, and retrieve that feed"
                            +"The super lightweight service cares not how you publish to it nor how you consume it."
                            +"This means you can bring your own client for both publishing and reading."
                        }

                        h2 { +"How it works" }

                        div {
                            h3 { +"1. Save articles" }
                            code {
                                +"POST /api/articles"
                                +"Headers: Authorization: Bearer {your-key}"
                                +"Body: ${
                                    jacksonObjectMapper().writeValueAsString(
                                        ArticleBody(
                                            url = "https://example.com/article-url",
                                        )
                                    )
                                }"
                            }
                            p {
                                +"When you find an interesting article, send its URL to RSS It Later using a browser extension or mobile app. "
                                +"The server fetches the article's metadata and adds it to your personal feed."
                            }
                        }

                        div {
                            h3 { +"2. Read anywhere" }
                            p {
                                +"Open your RSS reader (like Reeder or Inoreader) to access your personal feed. "
                                +"All your saved articles are there, ready to read in your favorite app."
                            }
                            code {
                                +"POST /api/articles/{your-user-id}"
                                +"Body: ${
                                    jacksonObjectMapper().writeValueAsString(
                                        ArticleBody(
                                            url = "https://example.com/article-url",
                                        )
                                    )
                                }"
                            }
                        }
                    }
                    a(classes = "github-fork-ribbon") {
                        href = "https://github.com/olaven/rss-it-later"
                        this.attributes["data-ribbon"] = "Fork me on GitHub"
                        this.attributes["title"] = "Fork me on GitHub"
                        +"Fork me on GitHub"
                    }
                }
            }
        }
        ctx.html(html)
    }
}