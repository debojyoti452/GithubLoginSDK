package com.swing.githubloginsdk.src.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import com.swing.githubloginsdk.R

class GithubSheetFragment : DialogFragment(), View.OnClickListener {

    private var gitWebView: WebView? = null
    val url =
        "https://github.com/login/oauth/authorize?client_id=31e1daafe57abcbd91ce&scope=public_repo%20read:user%20user:email"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gitWebView = view.findViewById(R.id.gitWebView)
        gitWebView?.settings?.javaScriptEnabled = true
        gitWebView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(request?.url.toString())
//                if (Uri.parse(url).host == url) {
//                    return false
//                }
//
//                Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
//                    startActivity(this)
//                }
                return true
            }
        }
        gitWebView?.loadUrl(url)
    }

    override fun onClick(p0: View?) {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_github_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
    }

    companion object {
        fun newInstance(): GithubSheetFragment {
            return GithubSheetFragment()
        }
    }
}
