package com.swing.githubloginsdk.src.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.google.gson.JsonParseException
import com.swing.githubloginsdk.src.model.AuthResult
import com.swing.githubloginsdk.src.model.GitRequest
import com.swing.githubloginsdk.src.network.GitClient
import com.swing.githubloginsdk.src.utils.Url
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


internal class GithubSDK private constructor(
    private val activity: Activity,
    private val gitClientId: String,
    private val gitSecret: String,
) : GitClient() {

    private var urlConnection: HttpURLConnection? = null

    private val urlBuilder = StringBuilder().apply {
        append(Url.baseUrl)
        append(StringBuilder().append(Url.clientId).append("=").append(gitClientId))
        append(StringBuilder().append("&").append(Url.scope))
        append(StringBuilder().append("&").append(Url.read))
        append(StringBuilder().append("&").append(Url.user))
    }

    @SuppressLint("LogNotTimber")
    fun callGithubAuth() {
        Log.d("KotlinActivity:", urlBuilder.toString())
        try {
            val webpage: Uri = Uri.parse(urlBuilder.toString())
            val myIntent = Intent(Intent.ACTION_VIEW, webpage)
            activity.startActivity(myIntent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    @SuppressLint("LogNotTimber")
    fun checkDeepLinkData() {
        val data = activity.intent.data
        if (data != null) {
            networkCall(data.getQueryParameter("code"))

//            Thread {
//                Log.d("KotlinActivity:", "${networkCall(data.getQueryParameter("code"))}")
//            }.start()
        }
    }

    private fun networkCall(code: String?): AuthResult? {
        try {
            val gitRequest = GitRequest(
                clientId = gitClientId,
                clientSecret = gitSecret,
                code = code
            )

            val url = URL(Url.baseUrl)
            val urlConnection = openPostConnection(url)
            writeToOutputStream(conn = urlConnection, gson.toJson(gitRequest))
            urlConnection.connect()

            val responseCode = urlConnection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readFromInputStream(conn = urlConnection)?.let { handleOperationResult(it) }
            }

        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (urlConnection != null) {
                close(conn = urlConnection!!)
            }
        }
        return null
    }

    @Throws(JsonParseException::class)
    private fun handleOperationResult(data: String): AuthResult? {
        return gson.fromJson(data, AuthResult::class.java)
    }

    companion object {
        fun newInstance(
            activity: Activity,
            gitClientId: String,
            gitSecret: String,
        ): GithubSDK {
            return GithubSDK(activity = activity, gitClientId = gitClientId, gitSecret = gitSecret)
        }
    }
}
