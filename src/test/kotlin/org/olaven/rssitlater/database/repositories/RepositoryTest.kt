import net.datafaker.Faker
import org.junit.jupiter.api.BeforeAll
import org.olaven.rssitlater.database.entities.Article
import org.olaven.rssitlater.database.entities.User
import org.olaven.rssitlater.database.entities.connectDatabase
import org.olaven.rssitlater.database.repositories.ArticleRepository
import org.olaven.rssitlater.database.repositories.UserRepository
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.Collections.list
import java.util.UUID
import java.util.concurrent.TimeUnit

abstract class RepositoryTest {


    companion object {

        val userRepository = UserRepository()
        val articleRepository = ArticleRepository()

        val faker = Faker()
        private val userCount = faker.number().numberBetween(3, 10)

        @JvmStatic
        protected val users: List<User>;

        @JvmStatic
        protected val articles: List<Article>;


        init {
            connectDatabase()
            users = List(userCount) { randomUser() }.mapNotNull { userRepository.insertUser(it.apiKey) }
            articles = users.flatMap { user ->
                val articleCount = faker.number().numberBetween(1, 25)
                val articles = List(articleCount) { randomArticle(user.id) }.mapNotNull { article ->
                    articleRepository.insertArticle(
                        title = article.title,
                        description = article.description,
                        url = article.url,
                        userId = user.id,
                    )
                }
                articles
            }
        }

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            connectDatabase()

        }


        private fun randomUser(): User {
            return User(
                id = UUID.randomUUID(),
                apiKey = UUID.randomUUID().toString(),
                createdAt = faker.timeAndDate().past(1, TimeUnit.HOURS)
                    .atOffset(ZoneOffset.UTC),
                lastSeen = faker.timeAndDate().past(1, TimeUnit.HOURS).atOffset(ZoneOffset.UTC),
            )
        }

        private fun randomArticle(userId: UUID): Article {
            return Article(
                id = UUID.randomUUID(),
                title = faker.lorem().words(2).joinToString(" "),
                description = faker.lorem().paragraph(),
                createdAt = faker.timeAndDate().past(1, TimeUnit.HOURS).atOffset(ZoneOffset.UTC),
                url = faker.internet().url(),
                userId = userId,
            )
        }
    }

}
