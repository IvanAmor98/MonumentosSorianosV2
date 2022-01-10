package com.example.monumentossorianosv2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class PreviewActivity extends AppCompatActivity {

    MonumentDTO monument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        monument = getIntent().getParcelableExtra("monument");

        ((TextView)findViewById(R.id.newName)).setText(monument.getName());
        ((TextView)findViewById(R.id.newType)).setText(monument.getType().toString());
        ((TextView)findViewById(R.id.newAddress)).setText(monument.getAddress());
        ((TextView)findViewById(R.id.newPhone)).setText(String.valueOf(monument.getPhone()));
        ((TextView)findViewById(R.id.newUrl)).setText(monument.getUrl());
        ((TextView)findViewById(R.id.newComment)).setText(monument.getComment());
        ((ImageView)findViewById(R.id.imageView)).setImageBitmap(monument.getImage());
    }

    public void save(View v) {
        MonumentDAO monumentDAO = new MonumentDAO(this);
        if (monumentDAO.saveMonument(monument)) {
            Toast.makeText(this, "Monumento guardado correctmente", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Ha habido un error, por favor, vuelva a intentarlo", Toast.LENGTH_LONG).show();
        }
    }

    public void discard(View v) {
        Toast.makeText(this, "Monumento descartado", Toast.LENGTH_LONG).show();
        finish();
    }
}