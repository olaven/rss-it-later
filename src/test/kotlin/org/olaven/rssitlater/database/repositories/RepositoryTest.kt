import net.datafaker.Faker
import org.olaven.rssitlater.database.entities.Article
import org.olaven.rssitlater.database.entities.User
import org.olaven.rssitlater.database.repositories.ArticleRepository
import org.olaven.rssitlater.database.repositories.UserRepository
import java.time.ZoneOffset
import java.util.UUID
import java.util.concurrent.TimeUnit

abstract class RepositoryTest : DatabaseTest() {


    companion object {

        val userRepository = UserRepository()
        val articleRepository = ArticleRepository()

        val faker = Faker()
        private val userCount = faker.number().numberBetween(3, 10)

        @JvmStatic
        protected val users: List<User>;

        @JvmStatic
        protected val testUser: User;

        @JvmStatic
        protected val articles: List<Article>;


        init {
            users = List(userCount) { generateRandomUser() }.mapNotNull { userRepository.insertUser(it.apiKey) }
            testUser = userRepository.findByApiKey(users.random().apiKey) ?: throw Exception("User not found")
            articles = users.flatMap { user ->
                val articleCount = faker.number().numberBetween(1, 25)
                val articles = List(articleCount) { generateRandomArticle(user.id) }.mapNotNull { article ->
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


        private fun generateRandomUser(): User {
            return User(
                id = UUID.randomUUID(),
                apiKey = UUID.randomUUID().toString(),
                createdAt = faker.timeAndDate().past(1, TimeUnit.HOURS)
                    .atOffset(ZoneOffset.UTC),
                lastSeen = faker.timeAndDate().past(1, TimeUnit.HOURS).atOffset(ZoneOffset.UTC),
            )
        }

        private fun generateRandomArticle(userId: UUID): Article {
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
