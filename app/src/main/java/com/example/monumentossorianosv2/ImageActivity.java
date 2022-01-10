package com.example.monumentossorianosv2;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        MonumentDTO monument = getIntent().getParcelableExtra("monument");
        ((ImageView)findViewById(R.id.imageView3)).setImageURI(Uri.parse(monument.getImageFullSize()));
    }
}