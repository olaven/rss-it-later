import io.javalin.http.Context
import io.javalin.http.Handler
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Test
import java.util.*

abstract class ProtectedHandlerTest(
    protected val handler: Handler
) : RepositoryTest() {
    private fun mockAuthorizedRequest(token: String?): Context {

        val requestMock = mockk<HttpServletRequest>(relaxed = true)
        val contextMock = mockk<Context>(relaxed = true)
        every { requestMock.getParameter("api-key") } returns token
        every { contextMock.req() } returns requestMock
        return contextMock
    }

    @Test
    fun `returns 400 if api key is not set`() {
        val context = mockAuthorizedRequest(null)
        handler.handle(context)
        verify { context.status(400) }
    }

    @Test
    fun `returns 401 if api key is invalid`() {

        val context = mockAuthorizedRequest(UUID.randomUUID().toString())
        handler.handle(context)
        verify { context.status(401) }
    }
}