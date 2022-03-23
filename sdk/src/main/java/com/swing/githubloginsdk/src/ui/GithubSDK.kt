package com.swing.githubloginsdk.src.ui

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import com.google.gson.JsonParseException
import com.swing.githubloginsdk.src.constants.Url
import com.swing.githubloginsdk.src.executor.BackgroundExecutor
import com.swing.githubloginsdk.src.model.AuthResult
import com.swing.githubloginsdk.src.model.GitRequest
import com.swing.githubloginsdk.src.network.GitRepository
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.SocketException
import java.net.URL

internal class GithubSDK private constructor(
    private val activity: Activity,
    private val gitClientId: String,
    private val gitSecret: String,
    private val scopes: String,
    val onSuccess: ((authResult: AuthResult) -> Unit),
    val onFailed: ((exception: Exception) -> Unit),
    private val backgroundExecutor: BackgroundExecutor = BackgroundExecutor()
) : GitRepository<AuthResult>() {

    init {
        initialize()
    }

    private var urlConnection: HttpURLConnection? = null

    private val urlBuilder = StringBuilder().apply {
        append(Url.baseUrl)
        append(StringBuilder().append("?").append(Url.clientId).append("=").append(gitClientId))
        append(scopes)
    }

    fun callGithubAuth() {
        try {
            val webpage: Uri = Uri.parse(urlBuilder.toString())
            val myIntent = Intent(Intent.ACTION_VIEW, webpage)
            activity.startActivity(myIntent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    fun checkDeepLinkData() {
        val data = activity.intent.data
        if (data != null && !data.getQueryParameter("code").isNullOrBlank()) {
            backgroundExecutor.execute {
                networkCall(data.getQueryParameter("code"))
            }
        }
    }

    @Throws(Exception::class)
    private fun networkCall(code: String?) {
        try {
            val gitRequest = GitRequest(
                clientId = gitClientId,
                clientSecret = gitSecret,
                code = code
            )

            val url = URL(Url.accessUrl)
            val urlConnection = openPostConnection(url)
            writeToOutputStream(conn = urlConnection, gson.toJson(gitRequest))
            urlConnection.connect()

            when (urlConnection.responseCode) {
                HttpURLConnection.HTTP_OK -> {
                    close(conn = urlConnection)
                    onSuccess(handleOperationResult(readFromInputStream(conn = urlConnection)))
                }
                else -> {
                    close(conn = urlConnection)
                    onFailed(Exception("Something went wrong.."))
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
            onFailed(SecurityException("${e.message}"))
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            onFailed(MalformedURLException("${e.message}"))
        } catch (e: IOException) {
            e.printStackTrace()
            onFailed(IOException("${e.message}"))
        } catch (e: SocketException) {
            e.printStackTrace()
            onFailed(SocketException("${e.message}"))
        } finally {
            if (urlConnection != null) {
                close(urlConnection!!)
            }
        }
    }

    @Throws(JsonParseException::class)
    private fun handleOperationResult(data: String): AuthResult {
        return gson.fromJson(data, AuthResult::class.java)
    }

    private fun onSuccess(handleOperationResult: AuthResult) {
        backgroundExecutor.getUiThread().post {
            onSuccess.invoke(handleOperationResult)
        }
    }

    private fun onFailed(e: Exception) {
        backgroundExecutor.getUiThread().post {
            onFailed.invoke(e)
        }
    }

    companion object {
        fun newInstance(
            activity: Activity,
            gitClientId: String,
            gitSecret: String,
            scopes: String,
            onSuccess: ((authResult: AuthResult) -> Unit),
            onFailed: ((exception: Exception) -> Unit),
        ): GithubSDK {
            return GithubSDK(
                activity = activity,
                gitClientId = gitClientId,
                gitSecret = gitSecret,
                scopes = scopes,
                onSuccess = onSuccess,
                onFailed = onFailed
            )
        }
    }
}
