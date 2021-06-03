package com.ss.universitiesdirectory.utils

import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

fun connectTo(address: String): Any {
    return try {
        (URL(address).openConnection() as HttpURLConnection).apply {
            this.requestMethod = "GET"
            this.connectTimeout = 15000
            this.readTimeout = 15000
            this.doInput = true
        }
    } catch (e: MalformedURLException) {
        e.printStackTrace()
        "Error : wrong url format"
    } catch (e: IOException) {
        e.printStackTrace()
        "Error : unable to establish connection"
    }
}