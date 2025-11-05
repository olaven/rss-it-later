import io.javalin.http.Header
import jakarta.servlet.http.HttpServletRequest

fun getApiKey(request: HttpServletRequest): String? {
    val unparsedHeaderValue = request.getHeader(Header.AUTHORIZATION) ?: return null

    val bearerPrefix = "Bearer "
    return unparsedHeaderValue
        .takeIf { it.startsWith(bearerPrefix) }
        ?.substringAfter(bearerPrefix)
        ?.trim()
}