package com.example.githublogin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.swing.githubloginsdk.GithubAuth;

public class JavaActivity extends AppCompatActivity {

    private GithubAuth githubAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        githubAuth = new GithubAuth.Builder("31e1daafe57abcbd91ce", "d875d932ff16bd79c259816371fba6b03c1dde8b")
                .setActivity(this)
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

    @Override
    protected void onResume() {
        super.onResume();
        githubAuth.onDeepLinkInitializer();
    }
}
