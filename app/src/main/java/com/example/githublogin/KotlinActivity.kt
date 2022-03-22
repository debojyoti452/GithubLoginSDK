package com.example.githublogin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.swing.githubloginsdk.GithubAuth
import com.swing.githubloginsdk.src.constants.Scopes

class KotlinActivity : AppCompatActivity(), View.OnClickListener {
    private val githubAuth by lazy {
        GithubAuth.Builder(BuildConfig.GIT_CLIENT_ID, BuildConfig.GIT_CLIENT_SECRET)
            .setActivity(this)
            .setOnSuccess {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
            .setOnFailed {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
            .setScopes(listOf(Scopes.PUBLIC_REPO, Scopes.USER_EMAIL))
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        findViewById<AppCompatButton>(R.id.openGithubAuth).setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        githubAuth.auth()
    }

    override fun onResume() {
        super.onResume()
        githubAuth.onDeepLinkInitializer()
    }
}
