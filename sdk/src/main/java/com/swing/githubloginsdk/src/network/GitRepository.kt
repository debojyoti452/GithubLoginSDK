package com.swing.githubloginsdk.src.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.swing.githubloginsdk.src.constants.Url
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.CookieHandler
import java.net.CookieManager
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets


internal abstract class GitRepository<R> {
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
        conn.setRequestProperty(Url.HEADER_ACCEPT, Url.CONTENT_TYPE_JSON)
    }

    @Throws(IOException::class)
    fun writeToOutputStream(conn: HttpURLConnection, data: String) {
        conn.outputStream.use { out -> out.write(data.toByteArray(StandardCharsets.UTF_8)) }
    }

    @Throws(IOException::class)
    fun readFromInputStream(conn: HttpURLConnection): String {
        conn.inputStream.use { ins ->
            InputStreamReader(ins).use { ir ->
                BufferedReader(ir).use { rd ->
                    return readFromBufferedReader(
                        rd
                    )
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun readFromBufferedReader(inputReader: BufferedReader): String {
        val buf = StringBuilder()
        var line: String?
        while (inputReader.readLine().also { line = it } != null) {
            buf.append(line)
        }
        return buf.toString()
    }

    @Throws(IOException::class)
    fun close(conn: HttpURLConnection) {
        conn.disconnect()
    }
}
