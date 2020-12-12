package com.ss.universitiesdirectory.RSS

import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

object Connector {
    fun connect(urlAddress: String?): Any {
        return try {
            val url = URL(urlAddress)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 15000
            connection.readTimeout = 15000
            connection.doInput = true
            connection
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            "Error : Wrong URL Format"
        } catch (e: IOException) {
            e.printStackTrace()
            "Error : Unable To Establish Connection"
        }
    }
}