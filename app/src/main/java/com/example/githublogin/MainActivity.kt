package com.example.githublogin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.swing.githubloginsdk.GithubAuth

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<AppCompatButton>(R.id.openKotlinActivity).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.openJavaActivity).setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.openKotlinActivity -> {
                startActivity(Intent(this, KotlinActivity::class.java))
            }

            R.id.openJavaActivity -> {
                startActivity(Intent(this, JavaActivity::class.java))
            }
        }
    }
}
