import org.junit.jupiter.api.Test
import org.olaven.rssitlater.database.repositories.UserRepository
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class UserRepositoryTest : RepositoryTest() {

    val repository = UserRepository()

    @Test
    fun `getting a user returns null if user does not exist`() {
        val nonExistentKey = UUID.randomUUID().toString()
        val user = repository.findByApiKey(nonExistentKey)
        assertNull(user)
    }

    @Test
    fun `can insert a user`() {
        val apiKey = UUID.randomUUID().toString();
        val user = repository.insertUser(
            apiKey = apiKey,
        )

        assertNotNull(user)
    }

    @Test
    fun `can insert a user and retrieve its articles`() {
        val apiKey = UUID.randomUUID().toString();
        val created = repository.insertUser(apiKey);
        val retrieved = repository.findByApiKey(apiKey)
        assertEquals(created, retrieved)
    }

}