import com.chimbori.crux.Crux
import com.chimbori.crux.api.Fields
import okhttp3.HttpUrl.Companion.toHttpUrl

data class ExtractedArticle(val title: String?, val description: String?, val url: String)
class ArticleExtractorService {
    private val crux = Crux()
    suspend fun extract(url: String): ExtractedArticle? {

        val result = crux.extractFrom(originalUrl = url.toHttpUrl())
        val title = result.metadata[Fields.TITLE] as? String;
        val description = result.metadata[Fields.DESCRIPTION] as? String;
        // TODO: add support for image?
        // val image = Fields.BANNER_IMAGE_URL
        return ExtractedArticle(
            title = title,
            description = description,
            url = url
        )


    }
}