package com.example.githublogin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.swing.githubloginsdk.GithubAuth

class KotlinActivity : AppCompatActivity(), View.OnClickListener {
    private val githubAuth by lazy {
        GithubAuth.Builder("31e1daafe57abcbd91ce", "d875d932ff16bd79c259816371fba6b03c1dde8b")
            .setActivity(this)
            .setOnSuccess {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
            .setOnFailed {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
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
