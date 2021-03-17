package com.munderhill.affirmation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void viewAffirmations(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, ViewAffirmationsActivity.class);
        startActivity(intent);

    }

    public void addOrEditAffirmations(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, AddOrEditAffirmationsActivity.class);
        startActivity(intent);
    }

    public void demo(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DemoActivity1.class);
        startActivity(intent);
    }
}
