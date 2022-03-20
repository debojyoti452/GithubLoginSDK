package com.example.githublogin;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.swing.githubloginsdk.GithubAuth;

public class JavaActivity extends AppCompatActivity {

    private GithubAuth githubAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        githubAuth = new GithubAuth.Builder("", "")
                .setContext(this)
                .setOnSuccess(message -> {
                    Toast.makeText(JavaActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    return null;
                })
                .setOnFailed(error -> {
                    Toast.makeText(JavaActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    return null;
                })
                .build();

        findViewById(R.id.button).setOnClickListener(view -> githubAuth.auth());
    }
}
