package com.ss.universitiesdirectory.repository.news

import android.content.Context
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.model.NewsModel
import com.ss.universitiesdirectory.utils.connectTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedInputStream
import javax.net.ssl.HttpsURLConnection

class NewsRepository(private val context: Context) {

    fun downloadRssData(address: String) = flow {
        emit(NewsStatus.NewsLoading)
        val connection = connectTo(address)
        if (!connection.toString().lowercase().startsWith("error")) {
            val httpsConnection = connection as HttpsURLConnection
            if (httpsConnection.responseCode == HttpsURLConnection.HTTP_OK) {
                emit(NewsStatus.NewsList(parseRssData(BufferedInputStream(httpsConnection.inputStream))))
            }
            else emit(NewsStatus.NewsFailed(httpsConnection.responseMessage))
        } else emit(NewsStatus.NewsFailed(context.getString(R.string.connection_failed)))
    }.flowOn(Dispatchers.IO)

    private fun parseRssData(inputStream: BufferedInputStream) : List<NewsModel> {
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