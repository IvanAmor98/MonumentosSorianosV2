package com.example.monumentossorianosv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MonumentListActivity extends AppCompatActivity {

    ListView lvMonuments;
    ListAdapter adapter;
    ArrayList<MonumentDTO> monuments;
    MonumentDAO monumentDAO = new MonumentDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monument_list);

        monumentDAO = new MonumentDAO(this);
        monuments = monumentDAO.listMonuments();

        lvMonuments = findViewById(R.id.monumentList);
        adapter = new ListAdapter(this, R.layout.monument_list_row, monuments);
        lvMonuments.setAdapter(adapter);

        lvMonuments.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String address = monuments.get(position).getAddress();
            address = address.replace(' ', '+');
            Uri.parse("google.navigation:q=" + address);
            startActivity(intent);
        });
    }
}

