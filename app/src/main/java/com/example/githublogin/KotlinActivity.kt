package com.example.githublogin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.swing.githubloginsdk.GithubAuth

class KotlinActivity : AppCompatActivity() {
    private val githubAuth by lazy {
        GithubAuth.Builder("", "")
            .setIsSafeWindow(isSafeWindow = false)
            .setContext(this)
            .setOnSuccess {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
            .setOnFailed {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        findViewById<AppCompatButton>(R.id.openGithubAuth).setOnClickListener {
            githubAuth.auth()
        }
    }
}
