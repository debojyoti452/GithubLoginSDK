package com.example.githublogin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.swing.githubloginsdk.GithubAuth;
import com.swing.githubloginsdk.src.constants.Scopes;

import java.util.Arrays;

public class JavaActivity extends AppCompatActivity {

    private GithubAuth githubAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        githubAuth = new GithubAuth.Builder(BuildConfig.GIT_CLIENT_ID, BuildConfig.GIT_CLIENT_SECRET)
                .setActivity(this)
                .setOnSuccess(message -> {
                    Toast.makeText(JavaActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    return null;
                })
                .setOnFailed(error -> {
                    Toast.makeText(JavaActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    return null;
                })
                .setScopes(Arrays.asList(Scopes.PUBLIC_REPO, Scopes.USER_EMAIL))
                .build();

        findViewById(R.id.button).setOnClickListener(view -> githubAuth.auth());
    }

    @Override
    protected void onResume() {
        super.onResume();
        githubAuth.onDeepLinkInitializer();
    }
}
