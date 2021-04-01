package com.munderhill.affirmation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.munderhill.affirmation.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void viewAffirmations(View view) {
        Intent intent = new Intent(this, ViewAffirmationsActivity.class);
        startActivity(intent);
    }

    public void addOrEditAffirmations(View view) {
        Intent intent = new Intent(this, AddOrEditAffirmationsActivity.class);
        startActivity(intent);
    }

    public void demo(View view) {
        Intent intent = new Intent(this, DemoActivity1.class);
        startActivity(intent);
    }
}
