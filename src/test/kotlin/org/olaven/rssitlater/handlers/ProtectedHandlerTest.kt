import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.Header
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Test
import java.util.*

abstract class ProtectedHandlerTest(
    protected val handler: Handler
) : RepositoryTest() {
    private fun mockAuthorizationHeader(token: String?): Context {

        val requestMock = mockk<HttpServletRequest>(relaxed = true)
        val contextMock = mockk<Context>(relaxed = true)
        val headerValue = token?.let { "Bearer $it" }
        every { requestMock.getHeader(Header.AUTHORIZATION) } returns headerValue
        every { contextMock.req() } returns requestMock
        return contextMock
    }

    @Test
    fun `returns 400 if api key is not set`() {
        val context = mockAuthorizationHeader(null)
        handler.handle(context)
        verify { context.status(400) }
    }

    @Test
    fun `returns 401 if api key is invalid`() {

        val context = mockAuthorizationHeader(UUID.randomUUID().toString())
        handler.handle(context)
        verify { context.status(401) }
    }
}