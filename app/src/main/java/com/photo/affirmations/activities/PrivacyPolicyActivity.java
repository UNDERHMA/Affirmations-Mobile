package com.photo.affirmations.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.photo.affirmations.R;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_privacy_policy);
        WebView webView = (WebView) findViewById(R.id.privacyPolicy);
        webView.loadUrl("file:///android_asset/Privacy_Policy.html");
    }

    // takes user back to home when back button on top is clicked.
    @Override
    public boolean onSupportNavigateUp() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        return true;
    }
}
