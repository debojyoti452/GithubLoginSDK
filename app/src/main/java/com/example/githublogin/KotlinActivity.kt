package com.example.githublogin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.swing.githubloginsdk.GithubAuth
import com.swing.githubloginsdk.src.constants.Scopes
import java.util.*

@SuppressLint("SetTextI18n")
class KotlinActivity : AppCompatActivity(), View.OnClickListener {

    private var userInformationTV: AppCompatTextView? = null

    private val githubAuth by lazy {
        GithubAuth.Builder(BuildConfig.GIT_CLIENT_ID, BuildConfig.GIT_CLIENT_SECRET)
            .setActivity(this)
            .setOnSuccess {
                if (it.accessToken == null) {
                    if (it.error != null && it.errorDescription != null) {
                        userInformationTV?.text = String.format(
                            Locale.getDefault(), "Error: %s\nError Desc: %s",
                            it.error, it.errorDescription
                        )
                    } else {
                        userInformationTV?.text = "No User Token Found."
                    }
                }else {
                    userInformationTV?.text = String.format(
                        Locale.getDefault(), "Token: %s...\nType: %s\nScopes: %s",
                        it.accessToken?.chunked((it.accessToken?.length ?: 0) / 2)?.firstOrNull(),
                        it.tokenType, it.scope
                    )
                }
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
        userInformationTV = findViewById(R.id.userInformationTV)
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
