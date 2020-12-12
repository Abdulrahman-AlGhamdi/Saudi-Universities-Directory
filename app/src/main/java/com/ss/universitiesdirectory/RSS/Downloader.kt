package com.ss.universitiesdirectory.RSS

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection

class Downloader(private val Address: String, private val listView: RecyclerView) : AsyncTask<Void?, Void?, Any>() {

    override fun doInBackground(vararg params: Void?): Any {
        return downloadData()
    }

    override fun onPostExecute(data: Any) {
        super.onPostExecute(data)
        if (data.toString().startsWith("Error")) {

        } else {
            RSSParser(data as InputStream, listView).execute()
        }
    }

    private fun downloadData(): Any {
        val connection = Connector.connect(Address)
        return if (connection.toString().startsWith("Error")) {
            connection.toString()
        } else try {
            val con = connection as HttpURLConnection
            val responseCode = con.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedInputStream(con.inputStream)
            } else "Error : Bad Response " + con.responseMessage
        } catch (e: IOException) {
            e.printStackTrace()
            "Error : Unable To Read"
        }
    }
}