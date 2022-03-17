package com.swing.githubloginsdk.src.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.swing.githubloginsdk.R
import timber.log.Timber

class GithubSheetFragment : AppCompatActivity(), View.OnClickListener {

    private var closeDialogBtn: AppCompatImageView? = null
    private var gitWebView: WebView? = null
    private val url =
        "https://github.com/login/oauth/authorize?client_id=31e1daafe57abcbd91ce&scope=public_repo%20read:user%20user:email"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_github_fragment)
        initialize()
        setWebView()

        val data: Uri? = intent?.data
        Timber.d(data.toString());
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView() {
        gitWebView?.settings?.javaScriptEnabled = true

        WebStorage.getInstance().deleteAllData()
        // Clear all the cookies
        CookieManager.getInstance().removeAllCookies(null)
        CookieManager.getInstance().flush()
        gitWebView?.clearHistory()
        gitWebView?.clearFormData()
        gitWebView?.clearCache(true)
        gitWebView?.clearSslPreferences()

        gitWebView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {

                Timber.d(request?.url.toString())
                view?.loadUrl(request?.url.toString())

//                if (Uri.parse(url).host == url) {
//                    return false
//                }

//                Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
//                    startActivity(this)
//                }
                return true
            }
        }
        gitWebView?.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                Timber.d(consoleMessage?.message().toString())
                return true
            }
        }
        gitWebView?.loadUrl(url)
    }

    private fun initialize() {
        closeDialogBtn = findViewById(R.id.closeDialogBtn)
        gitWebView = findViewById(R.id.gitWebView)
        closeDialogBtn?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.closeDialogBtn -> {
                finish()
            }
        }
    }

    companion object {
        fun newInstance(): GithubSheetFragment {
            return GithubSheetFragment()
        }
    }
}
