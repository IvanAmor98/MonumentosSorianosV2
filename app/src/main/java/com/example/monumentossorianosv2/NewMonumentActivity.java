package com.example.monumentossorianosv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class NewMonumentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_monument);
    }

    public void saveMonument(View v) {
        MonumentDTO monument = new MonumentDTO(
                ((TextView)findViewById(R.id.nameInput)).getText().toString(),
                MonumentDTO.MonumentType.values()[((Spinner)findViewById(R.id.typeSpinner)).getSelectedItemPosition()],
                ((TextView)findViewById(R.id.addressInput)).getText().toString(),
                Integer.parseInt(((TextView)findViewById(R.id.phoneInput)).getText().toString()),
                ((TextView)findViewById(R.id.urlInput)).getText().toString(),
                ((TextView)findViewById(R.id.commentInput)).getText().toString()
        );
        Intent intent = new Intent(this, PreviewActivity.class);
        intent.putExtra("monument", monument);
        startActivity(intent);
        finish();
    }
}