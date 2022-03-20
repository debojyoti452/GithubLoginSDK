package com.swing.githubloginsdk.src.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.swing.githubloginsdk.src.utils.Url
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.CookieHandler
import java.net.CookieManager
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets


abstract class GitClient {
    val gson: Gson = GsonBuilder().create()

    fun initialize() {
        if (CookieHandler.getDefault() == null) {
            CookieHandler.setDefault(CookieManager())
        }
    }

    @Throws(IOException::class)
    fun openPostConnection(url: URL): HttpURLConnection {
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = Url.HTTP_POST
        setConnProperties(conn)
        conn.doInput = true
        conn.doOutput = true
        return conn
    }

    private fun setConnProperties(conn: HttpURLConnection) {
        conn.connectTimeout = Url.TIMEOUT_CONNECT
        conn.readTimeout = Url.TIMEOUT_READ
        conn.setRequestProperty(Url.HEADER_CONTENT_TYPE, Url.CONTENT_TYPE_JSON)
    }

    @Throws(IOException::class)
    open fun writeToOutputStream(conn: HttpURLConnection, data: String) {
        conn.outputStream.use { out -> out.write(data.toByteArray(StandardCharsets.UTF_8)) }
    }

    @Throws(IOException::class)
    open fun readFromInputStream(conn: HttpURLConnection): String? {
        conn.inputStream.use { `in` ->
            InputStreamReader(`in`).use { ir ->
                BufferedReader(ir).use { rd ->
                    return readFromBufferedReader(
                        rd
                    )
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun readFromBufferedReader(`in`: BufferedReader): String? {
        val buf = StringBuilder()
        var line: String?
        while (`in`.readLine().also { line = it } != null) {
            buf.append(line)
        }
        return buf.toString()
    }

    fun close(conn: HttpURLConnection) {
        conn.disconnect()
    }
}
