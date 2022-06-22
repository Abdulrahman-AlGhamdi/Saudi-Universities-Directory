package com.ss.universitiesdirectory.repository.news

import com.ss.universitiesdirectory.data.model.news.NewsModel
import com.ss.universitiesdirectory.utils.connectTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

class NewsRepository @Inject constructor() {

    private val _newsStatus = MutableStateFlow<NewsStatus>(NewsStatus.NewsLoading)
    val newsStatus = _newsStatus.asStateFlow()

    suspend fun downloadRssData(address: String) = withContext(Dispatchers.IO) {
        _newsStatus.value = NewsStatus.NewsLoading
        val connection = connectTo(address)

        try {
            val httpsConnection = connection as HttpsURLConnection
            val newsList = parseRssData(httpsConnection.inputStream)
            _newsStatus.value = NewsStatus.NewsList(newsList)
        } catch (exception: Exception) {
            exception.localizedMessage?.let { _newsStatus.value = NewsStatus.NewsFailed(it) }
        }
    }

    private fun parseRssData(inputStream: InputStream): List<NewsModel> {
        val parser = XmlPullParserFactory.newInstance().newPullParser()
        parser.setInput(inputStream, null)

        var value = ""
        var title = ""
        var description = ""
        var date = ""
        var link = ""
        val newsList = arrayListOf<NewsModel>()

        while (parser.eventType != XmlPullParser.END_DOCUMENT) {
            when (parser.eventType) {
                XmlPullParser.START_TAG -> if (parser.name == "item") {
                    newsList.add(NewsModel(title, description, link, date))
                }
                XmlPullParser.TEXT -> {
                    value = parser.text
                }
                XmlPullParser.END_TAG -> {
                    when (parser.name) {
                        "title" -> title = value
                        "description" -> description = value
                        "pubDate" -> date = value
                        "link" -> link = value
                    }
                }
            }
            parser.next()
        }

        return newsList
    }

    sealed class NewsStatus {
        object NewsLoading : NewsStatus()
        data class NewsFailed(val message: String) : NewsStatus()
        data class NewsList(val newsList: List<NewsModel>) : NewsStatus()
    }
}