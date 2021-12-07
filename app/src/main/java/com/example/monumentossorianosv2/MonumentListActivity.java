package com.example.monumentossorianosv2;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MonumentListActivity extends AppCompatActivity {

    ListView lvMonumentos;
    ListAdapter adapter;
    ArrayList<MonumentDTO> monumentos;
    MonumentDAO monumentDAO = new MonumentDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monument_list);

        monumentDAO = new MonumentDAO(this);
        monumentos = monumentDAO.listMonuments();

        lvMonumentos = findViewById(R.id.monumentList);
        adapter = new ListAdapter(this, R.layout.monument_list_row, monumentos);
        lvMonumentos.setAdapter(adapter);

        lvMonumentos.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String address = monumentos.get(position).getAddress();
            address.replace(' ', '+');
            Uri.parse("google.navigation:q=" + address);
            startActivity(intent);
        });
    }
}

