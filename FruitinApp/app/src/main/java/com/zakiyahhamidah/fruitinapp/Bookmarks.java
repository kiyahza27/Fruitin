package com.zakiyahhamidah.fruitinapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Bookmarks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);


        getSupportActionBar().setTitle("Bookmarks");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}