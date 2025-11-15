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
            put("--accent", "#4a7c59")
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
            marginBottom = 1.rem
        }

        rule("h2") {
            marginTop = 2.5.rem
            marginBottom = 1.rem
        }

        rule(".hero") {
            marginBottom = 2.rem
        }

        rule(".tagline") {
            fontSize = 1.2.rem
            marginBottom = 1.5.rem
        }

        rule(".cta-button") {
            display = Display.inlineBlock
            backgroundColor = Color("var(--accent)")
            color = Color.white
            padding = Padding(0.75.rem, 1.5.rem)
            put("text-decoration", "none")
            border = Border(2.px, BorderStyle.solid, Color("var(--accent)"))
            cursor = Cursor.pointer
            marginTop = 1.rem
            marginBottom = 1.rem
        }

        rule(".cta-button:hover") {
            backgroundColor = Color("var(--paper)")
            color = Color("var(--accent)")
        }

        rule("code") {
            display = Display.block
            backgroundColor = Color("var(--bg)")
            padding = Padding(1.rem)
            border = Border(1.px, BorderStyle.solid, Color("var(--border)"))
            whiteSpace = WhiteSpace.pre
            overflowX = Overflow.auto
            marginTop = 0.5.rem
            marginBottom = 0.5.rem
        }

        rule(".api-section") {
            marginTop = 2.5.rem
        }

        rule(".flow-diagram") {
            display = Display.flex
            alignItems = Align.center
            justifyContent = JustifyContent.spaceBetween
            put("gap", "2rem")
            marginTop = 2.rem
            marginBottom = 2.rem
            padding = Padding(2.rem)
            backgroundColor = Color("var(--bg)")
            border = Border(1.px, BorderStyle.solid, Color("var(--border)"))
        }

        rule(".flow-column") {
            display = Display.flex
            flexDirection = FlexDirection.column
            put("gap", "1rem")
            flex = Flex(1.0)
        }

        rule(".flow-column.center") {
            flex = Flex(1.5)
            alignItems = Align.center
            textAlign = TextAlign.center
            justifyContent = JustifyContent.center
        }

        rule(".logo-box") {
            width = 80.px
            height = 80.px
            backgroundColor = Color("var(--paper)")
            border = Border(2.px, BorderStyle.dashed, Color("var(--border)"))
            display = Display.flex
            alignItems = Align.center
            justifyContent = JustifyContent.center
            fontSize = 0.75.rem
            color = Color("#999")
        }

        rule(".service-box") {
            padding = Padding(2.rem)
            backgroundColor = Color("var(--accent)")
            color = Color.white
            border = Border(2.px, BorderStyle.solid, Color("var(--text)"))
            fontWeight = FontWeight.bold
            fontSize = 1.5.rem
        }

        rule(".arrow") {
            fontSize = 2.rem
            color = Color("var(--text)")
        }
    }

    private val mobileStyles = """
        @media (max-width: 768px) {
            body {
                padding: 1rem;
            }

            main {
                padding: 2rem 1.5rem;
            }

            .flow-diagram {
                flex-direction: column !important;
                gap: 1.5rem !important;
                padding: 1rem !important;
            }

            .flow-column {
                width: 100%;
                flex-direction: row !important;
                flex-wrap: wrap;
                justify-content: center;
            }

            .logo-box {
                width: 60px;
                height: 60px;
                font-size: 0.7rem;
            }

            .service-box {
                padding: 1.5rem;
                font-size: 1.2rem;
            }

            .arrow {
                transform: rotate(90deg);
                font-size: 1.5rem;
            }
        }
    """

    override fun handle(ctx: Context) {
        val html = buildString {
            appendHTML().html {
                head {
                    title { +"RSS It Later" }
                    meta(charset = "UTF-8")
                    meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
                    link {
                        rel = "stylesheet"
                        href = "https://cdnjs.cloudflare.com/ajax/libs/github-fork-ribbon-css/0.2.3/gh-fork-ribbon.min.css"
                    }
                    style {
                        unsafe {
                            raw(styles.toString())
                            raw(mobileStyles)
                        }
                    }
                }
                body {
                    main {
                        // Hero Section
                        div(classes = "hero") {
                            h1 { +"RSS It Later" }
                            p(classes = "tagline") {
                                +"Save articles from anywhere. Read them in your favorite RSS reader."
                            }
                            p {
                                +"A lightweight service that lets you build your own read-it-later workflow. "
                                +"Use any HTTP client to save articles, and any RSS reader to consume them."
                            }
                            a(classes = "cta-button") {
                                href = "mailto:olav@sundfoer.com?subject=RSS It Later API Key Request"
                                +"Get Your API Key"
                            }
                        }

                        // Flow Diagram
                        div(classes = "flow-diagram") {
                            // Left column - Input sources
                            div(classes = "flow-column") {
                                div(classes = "logo-box") { +"Safari" }
                                div(classes = "logo-box") { +"Firefox" }
                                div(classes = "logo-box") { +"iOS" }
                                div(classes = "logo-box") { +"Android" }
                            }

                            // Arrow
                            div(classes = "arrow") { +"→" }

                            // Center column - RSS It Later
                            div(classes = "flow-column center") {
                                div(classes = "service-box") {
                                    +"RSS It Later"
                                }
                            }

                            // Arrow
                            div(classes = "arrow") { +"→" }

                            // Right column - Output readers
                            div(classes = "flow-column") {
                                div(classes = "logo-box") { +"Reeder 5" }
                                div(classes = "logo-box") { +"Inoreader" }
                                div(classes = "logo-box") { +"Feedly" }
                                div(classes = "logo-box") { +"Desktop" }
                            }
                        }

                        // API Reference
                        div(classes = "api-section") {
                            h2 { +"API Reference" }

                            h3 { +"Save Article" }
                            code {
                                +"POST /feed/articles?api-key={your-key}\n"
                                +"Content-Type: application/json\n\n"
                                +"${
                                    jacksonObjectMapper().writeValueAsString(
                                        ArticleBody(url = "https://example.com/article-url")
                                    )
                                }\n\n"
                                +"Response: 201 Created"
                            }

                            h3 { +"Get Feed" }
                            code {
                                +"GET /feed?api-key={your-key}\n\n"
                                +"Response: RSS/Atom XML feed"
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
