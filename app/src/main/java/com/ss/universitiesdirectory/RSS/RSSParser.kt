package com.ss.universitiesdirectory.RSS

import android.os.AsyncTask
import android.widget.ListView
import androidx.recyclerview.widget.RecyclerView
import com.ss.universitiesdirectory.Adapter.NewsAdapter
import com.ss.universitiesdirectory.Model.NewsModel
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.util.*

class RSSParser(private val stream: InputStream, private val listView: RecyclerView) : AsyncTask<Void?, Void?, Boolean>() {

    private var title: String? = ""
    private var link: String? = ""
    private var description: String? = ""
    private var date: String? = ""
    private lateinit var model: NewsModel
    private val newsModels = ArrayList<NewsModel>()

    override fun doInBackground(vararg params: Void?): Boolean {
        return parseRSS()
    }

    override fun onPostExecute(isParsed: Boolean) {
        super.onPostExecute(isParsed)

        if (isParsed)
            listView.adapter = NewsAdapter(newsModels)
    }

    private fun parseRSS(): Boolean {
        try {
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(stream, null)
            var event = parser.eventType
            var value: String? = null
            newsModels.clear()
            do {
                val name = parser.name
                when (event) {
                    XmlPullParser.START_TAG -> if (name == "item") {
                        model = NewsModel(title, description, link, date)
                    }
                    XmlPullParser.TEXT -> value = parser.text
                    XmlPullParser.END_TAG -> {
                        when (name) {
                            "title" ->
                                title = value
                            "description" ->
                                description = value
                            "pubDate" ->
                                date = value
                            "link" ->
                                link = value
                        }
                        if (name == "item") {
                            newsModels.add(model)
                        }
                    }
                }
                event = parser.next()
            } while (event != XmlPullParser.END_DOCUMENT)
            return true
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }
}